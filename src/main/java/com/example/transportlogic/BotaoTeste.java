package com.example.transportlogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import com.example.transportlogic.controller.TransportadoraController;
import com.example.transportlogic.model.CalculoModel;
import com.example.transportlogic.model.TransportadoraModel;

/**
 * Esta a classe que controla a ação do botão Teste
 * 
 * @author Alex Juno Bócoli
 *
 */
public class BotaoTeste implements ActionListener {
	
	private JTextField edit1;
	private JTextField edit2;
	private JTextField edit3;
	private JComboBox<String> box1;
	private JComboBox<String> box2;
	
	/**
	 * Construtor da classe
	 * @param edit1 o campo onde é informada a origem
	 * @param edit2 o campo onde é informado o destino
	 * @param edit3 o campo onde é informada a distância
	 * @param box1 a caixa de seleção onde é informado o tipo de transporte
	 * @param box2 a caixa de seleção onde é informada a prioridade
	 */
	public BotaoTeste(JTextField edit1, JTextField edit2, JTextField edit3, JComboBox<String> box1, JComboBox<String> box2) {
		this.edit1 = edit1;
		this.edit2 = edit2;
		this.edit3 = edit3;
		this.box1 = box1;
		this.box2 = box2;
	}

	/**
	 * Método responsável por realizar as ações do botão Teste
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Objeto instanciado da classe TransportadoraController
		TransportadoraController tc = new TransportadoraController();
		tc.removerCalculosSql();
		
		// Objeto que armazenará todas as transportadoras retornadas do MySQL e do WebService Rest
		ArrayList<TransportadoraModel> transp = new ArrayList<TransportadoraModel>();
		
		// Objeto que ajusta a precisão para duas casas decimais
		DecimalFormat df = new DecimalFormat("###,##0.00");
		
		List<TransportadoraModel> transportadoras1 = new ArrayList<TransportadoraModel>();
		List<TransportadoraModel> transportadoras2 = new ArrayList<TransportadoraModel>();;
		List<CalculoModel> calculos = new ArrayList<CalculoModel>();
		List<CalculoModel> melhores = new ArrayList<CalculoModel>();
		
		/**
		 * Teste 1
		 */

		edit1.setText("São Paulo - SP");
		edit2.setText("Manaus - AM");
		edit3.setText("3875");
		box1.setSelectedIndex(2);
		box2.setSelectedIndex(0);
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		transportadoras1 = tc.consultarSql(2);
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		transportadoras2 = tc.consultarRest(2);
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		// Calcula custo e prazo de acordo com a prioridade escolhida
		calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, 3875, 0);
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		melhores = new ArrayList<CalculoModel>();
		melhores = tc.consultarCalculoSql(calculos.get(0).getCusto(), 0);
		
		JOptionPane.showMessageDialog(null, "São Paulo - SP -> Manaus - AM; Distância: 3875 Km; Transporte aéreo; Menor custo\n" 
			+ "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo() 
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m", 
							"Teste 1", JOptionPane.INFORMATION_MESSAGE);

		transp.clear();
		calculos.clear();
		melhores.clear();
		tc.removerCalculosSql();
		
		/**
		 * Teste 2
		 */
		
		edit1.setText("Florianópolis - SC");
		edit2.setText("Campinas - SP");
		edit3.setText("762");
		box1.setSelectedIndex(1);
		box2.setSelectedIndex(0);
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		transportadoras1 = tc.consultarSql(1);
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		transportadoras2 = tc.consultarRest(1);
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		// Calcula custo e prazo de acordo com a prioridade escolhida
		calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, 762, 0);
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		melhores = new ArrayList<CalculoModel>();
		melhores = tc.consultarCalculoSql(calculos.get(0).getCusto(), 0);
		
		JOptionPane.showMessageDialog(null, "Florianópolis - SC -> Campinas - SP; Distância: 762 Km; Transporte terrestre; Menor custo\n" 
			+ "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo() 
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m", 
							"Teste 2", JOptionPane.INFORMATION_MESSAGE);
		
		transp.clear();
		calculos.clear();
		melhores.clear();
		tc.removerCalculosSql();
		
		/**
		 * Teste 3
		 */
		
		edit1.setText("Salvador - BA");
		edit2.setText("Belém - PA");
		edit3.setText("2018");
		box1.setSelectedIndex(0);
		box2.setSelectedIndex(1);
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		transportadoras1 = tc.consultarSql(0);
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		transportadoras2 = tc.consultarRest(0);
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		// Calcula custo e prazo de acordo com a prioridade escolhida
		calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, 2018, 1);
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		melhores = new ArrayList<CalculoModel>();
		melhores = tc.consultarCalculoSql(calculos.get(0).getPrazo(), 1);
		
		JOptionPane.showMessageDialog(null, "Salvador - BA -> Belém - PA; Distância: 2018 Km; Transporte terrestre/aéreo; Menor prazo\n" 
			+ "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo() 
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m", 
							"Teste 3", JOptionPane.INFORMATION_MESSAGE);
		
		transp.clear();
		calculos.clear();
		melhores.clear();
		tc.removerCalculosSql();
		
		/**
		 * Teste 4
		 */
		
		edit1.setText("São Paulo - SP");
		edit2.setText("Assunção - PAR");
		edit3.setText("1350");
		box1.setSelectedIndex(0);
		box2.setSelectedIndex(1);
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		transportadoras1 = tc.consultarSql(0);
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		transportadoras2 = tc.consultarRest(0);
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		// Calcula custo e prazo de acordo com a prioridade escolhida
		calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, 1350, 1);
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		melhores = new ArrayList<CalculoModel>();
		melhores = tc.consultarCalculoSql(calculos.get(0).getPrazo(), 1);
		
		JOptionPane.showMessageDialog(null, "São Paulo - SP -> Assunção - PAR; Distância: 1350 Km; Transporte terrestre/aéreo; Menor prazo\n" 
			+ "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo() 
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m", 
							"Teste 4", JOptionPane.INFORMATION_MESSAGE);
		
		transp.clear();
		calculos.clear();
		melhores.clear();
		tc.removerCalculosSql();
		
		/**
		 * Teste 5
		 */
		
		edit1.setText("Salvador - BA");
		edit2.setText("Brasília - DF");
		edit3.setText("1449");
		box1.setSelectedIndex(1);
		box2.setSelectedIndex(1);
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		transportadoras1 = tc.consultarSql(1);
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		transportadoras2 = tc.consultarRest(1);
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		// Calcula custo e prazo de acordo com a prioridade escolhida
		calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, 1449, 1);
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		melhores = new ArrayList<CalculoModel>();
		melhores = tc.consultarCalculoSql(calculos.get(0).getPrazo(), 1);
		
		JOptionPane.showMessageDialog(null, "Salvador - BA -> Brasília - DF; Distância: 1449 Km; Transporte terrestre; Menor prazo\n" 
			+ "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo() 
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m", 
							"Teste 4", JOptionPane.INFORMATION_MESSAGE);
		
	}

}
