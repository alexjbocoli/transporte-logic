package com.example.transportlogic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.transportlogic.model.CalculoModel;
import com.example.transportlogic.model.TipoTransporteModel;
import com.example.transportlogic.model.TransportadoraModel;

/**
 * Esta é a classe que controla a transportadora
 * 
 * @author Alex Juno Bócoli
 *
 */
public class TransportadoraController {
	EntityManagerFactory emf;
	EntityManager em;
	
	/**
	 * Construtor da classe, também responsável por instanciar o objeto da transportadora 1
	 */
	public TransportadoraController() {
		emf = Persistence.createEntityManagerFactory("transporte");
		em = emf.createEntityManager();
		
		TransportadoraModel t1 = new TransportadoraModel(1, "Terrestre", "Transportadora 1", 50, 60);
		this.adicionarSql(t1);
	}
	
	/**
	 * Método responsável por adicionar a transportadora ao banco de dados MySQL
	 * @param transp a transportadora para inserir no banco MySQL
	 */
	public void adicionarSql(TransportadoraModel transp) {
		Query consulta = em.createQuery("select transportadora from TransportadoraModel transportadora where nome = '" + transp.getNome() + "'"); 
		
		if(consulta.getResultList().isEmpty()) {
			em.getTransaction().begin();
			em.merge(transp);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Função responsável por consultar as transportadoras no banco de dados MySQL
	 * @param tipoTransporte o tipo de transporte informado para fazer a consulta
	 * @return uma lista com as transportadoras retornadas na consulta
	 */
	public List<TransportadoraModel> consultarSql(int tipoTransporte) {
		em.getTransaction().begin();
		Query consulta;
		
		if(tipoTransporte == 0) {
			consulta = em.createQuery("select transportadora from TransportadoraModel transportadora");
		}
		else {
			consulta = em.createQuery("select transportadora from TransportadoraModel transportadora where idTipo = " + tipoTransporte);
		}
		
		List<TransportadoraModel> transportadoras = consulta.getResultList();
		em.getTransaction().commit();
		
		return transportadoras;
	}
	
	/**
	 * Método responsável por adicionar o cálculo realizado ao banco de dados MySQL
	 * @param calc o cálculo para inserir no banco MySQL
	 */
	public void adicionarCalculoSql(CalculoModel calc) {
		em.getTransaction().begin();
		em.merge(calc);
		em.getTransaction().commit();
	}
	
	/**
	 * Método responsável por limpar os cálculos do banco de dados MySQL
	 */
	public void removerCalculosSql() {
		em.getTransaction().begin();
		Query consulta = em.createNativeQuery("delete calculo from calculo");
		consulta.executeUpdate();
		em.getTransaction().commit();
	}
	
	/**
	 * Função responsável por consultar os cálculos no banco de dados MySQL
	 * @param melhorPrioridade a coluna do banco de dados (custo ou prazo) de acordo com a prioridade informada
	 * @param prioridade a prioridade informada
	 * @return uma lista ordenada com os cálculos das melhores transportadoras de acordo com a prioridade
	 */
	public List<CalculoModel> consultarCalculoSql(Double melhorPrioridade, int prioridade) {
		em.getTransaction().begin();
		Query consulta = null;
		
		if(prioridade == 0) {
			consulta = em.createQuery("select calculo from CalculoModel calculo where custo = " + melhorPrioridade);
		}
		else if(prioridade == 1) {
			consulta = em.createQuery("select calculo from CalculoModel calculo where prazo = " + melhorPrioridade);
		}
		
		//Query consulta = (prioridade == 0) ? em.createQuery("select calculo from CalculoModel calculo where custo = " + melhorPrioridade) : em.createQuery("select calculo from CalculoModel calculo where prazo = " + melhorPrioridade); 
		
		List<CalculoModel> melhores = consulta.getResultList();
		em.getTransaction().commit();
		
		return melhores;		
	}
	
	/**
	 * Função responsável por consultar os cálculos em que houve empate de custo e prazo
	 * @param melhorCusto a coluna custo do banco de dados
	 * @param melhorPrazo a coluna prazo do banco de dados
	 * @return uma lista ordenada com os cálculos das melhores transportadoras empatadas
	 */
	public List<CalculoModel> consultarCalculoEmpateSql(Double melhorCusto, Double melhorPrazo){
		em.getTransaction().begin();
		Query consulta = em.createQuery("select calculo from CalculoModel calculo where custo = " + melhorCusto + " and prazo = " + melhorPrazo);
		List<CalculoModel> melhoresEmpate = consulta.getResultList();
		em.getTransaction().commit();
		
		return melhoresEmpate;
	}
	
	/**
	 * Função responsável por consultar no webservice rest as transportadoras de acordo com o tipo de transporte
	 * @param tipoTransporte o tipo de transporte informado para fazer a consulta
	 * @return uma lista com as transportadoras do tipo informado
	 */
	public List<TransportadoraModel> consultarRest(int tipoTransporte) {
		List<TransportadoraModel> transportadoras = null;
		
		try {
			URL url;
			if(tipoTransporte == 0) {
				url = new URL("http://localhost:8080/service/transportadora");
			}
			else {
				url = new URL("http://localhost:8080/service/transportadora/tipoTransporte=" + tipoTransporte);
			}
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Falha : Código de erro HTTP : " + con.getResponseCode());
            }
            
            InputStreamReader in = new InputStreamReader(con.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output = br.readLine();
            con.disconnect();
            
            ObjectMapper mapper = new ObjectMapper();
            transportadoras = Arrays.asList(mapper.readValue(output, TransportadoraModel[].class));         
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return transportadoras;
	}
	
	/**
	 * Função responsável por calcular o custo e o prazo para cada transportadora e armazenar em uma lista de cálculos
	 * @param transportadoras a lista de transportadoras consultadas de acordo com o tipo de transporte
	 * @param distancia a distância informada entre as duas cidades
	 * @param prioridade a prioridade informada
	 * @return uma lista de cálculos das transportadoras ordenadas em ordem crescente de acordo com a prioridade escolhida
	 */
	public ArrayList<CalculoModel> calculaPrecoTempo(List<TransportadoraModel> transportadoras, double distancia, int prioridade) {
		Double custo;
		Double prazo;
		
		ArrayList<CalculoModel> calculos = new ArrayList<CalculoModel>();
		
		// Realização dos cálculos, criação do modelo e armazenamento em ArrayList
		for(int i = 0; i < transportadoras.size(); i++) {
			custo = (distancia * transportadoras.get(i).getValor())/10;
			prazo = distancia * transportadoras.get(i).getTempo();
				
			CalculoModel calc = new CalculoModel(transportadoras.get(i).getIdTipo(), transportadoras.get(i).getNomeTipo(), transportadoras.get(i).getNome(), custo, prazo);
			calculos.add(calc);
		}
		
		// Prioridade de custo
		if (prioridade == 0) {
			//Comparator<CalculoModel> compareByCusto = (CalculoModel c1, CalculoModel c2) -> c1.getCusto().compareTo(c2.getCusto());
			//Collections.sort(calculos, compareByCusto);
			
			Collections.sort(calculos, Comparator.comparing(CalculoModel::getCusto)
					.thenComparing(CalculoModel::getPrazo));
		}
		// Prioridade de prazo
		else if (prioridade == 1){
			//Comparator<CalculoModel> compareByPrazo = (CalculoModel c1, CalculoModel c2) -> c1.getPrazo().compareTo(c2.getPrazo());
			//Collections.sort(calculos, compareByPrazo);
			
			Collections.sort(calculos, Comparator.comparing(CalculoModel::getPrazo)
		            .thenComparing(CalculoModel::getCusto));
		}
		
		return calculos;
	}
}
