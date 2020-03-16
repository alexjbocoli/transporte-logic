package com.example.transportlogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.example.transportlogic.controller.TransportadoraController;
import com.example.transportlogic.model.CalculoModel;
import com.example.transportlogic.model.TransportadoraModel;

/**
 * Esta a classe que controla a ação do botão Calcular
 * 
 * @author Alex Juno Bócoli
 *
 */
public class BotaoCalcular implements ActionListener{

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
	public BotaoCalcular(JTextField edit1, JTextField edit2, JTextField edit3, JComboBox<String> box1, JComboBox<String> box2) {
		this.edit1 = edit1;
		this.edit2 = edit2;
		this.edit3 = edit3;
		this.box1 = box1;
		this.box2 = box2;
	}
	
	/**
	 * Método responsável por realizar as ações do botão Calcular
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
		
		String origem = edit1.getText();
		String destino = edit2.getText();
		String distancia = edit3.getText();
		int tipoTransporte = box1.getSelectedIndex();
		int prioridade = box2.getSelectedIndex();
		
		if("".equals(origem.trim())) {
			JOptionPane.showMessageDialog(null, "Digite a cidade de origem!");
			return;
		}

		if("".equals(destino.trim())) {
			JOptionPane.showMessageDialog(null, "Digite a cidade de destino!");
			return;
		}
		
		if("".equals(distancia.trim())) {
			JOptionPane.showMessageDialog(null, "Digite a distância entre as cidades!");
			return;
		}
		
		// Consulta das transportadoras pelo tipo de transporte (MySQL)
		List<TransportadoraModel> transportadoras1 = tc.consultarSql(tipoTransporte);
		
		// Consulta das transportadoras pelo tipo de transporte (WebService Rest)
		List<TransportadoraModel> transportadoras2 = tc.consultarRest(tipoTransporte);
		
		// União das listas para facilitar os cálculos
		for(TransportadoraModel t : transportadoras1) {	
			transp.add(t);
		}
		for(TransportadoraModel t : transportadoras2) {
			transp.add(t);
		}
		
		// Calcula custo e prazo de acordo com a prioridade escolhida
		Double distanciaConv = Double.parseDouble(distancia);
		List<CalculoModel> calculos = new ArrayList<CalculoModel>(); 
		calculos = tc.calculaPrecoTempo(transp, distanciaConv, prioridade);
		
		for(CalculoModel c : calculos) {			
			tc.adicionarCalculoSql(c);
		}
		
		// Lista as melhores transportadoras de acordo com a prioridade escolhida
		List<CalculoModel> melhores = new ArrayList<CalculoModel>();
		List<CalculoModel> melhoresEmpate = new ArrayList<CalculoModel>();
		
		if(prioridade == 0) {
			melhores = tc.consultarCalculoSql(calculos.get(0).getCusto(), prioridade);
		}
		else if(prioridade == 1) {
			melhores = tc.consultarCalculoSql(calculos.get(0).getPrazo(), prioridade);
		}
		
		melhoresEmpate = tc.consultarCalculoEmpateSql(calculos.get(0).getCusto(), calculos.get(0).getPrazo());
		
		if(melhoresEmpate.size() <= 1) {
			JOptionPane.showMessageDialog(null, "A melhor transportadora para as condições informadas é:\n"
				+ melhores.get(0).getNome() + "; Tipo de transporte: " + melhores.get(0).getNomeTipo()
					+ "; Custo: R$" + df.format(melhores.get(0).getCusto()) + "; Prazo: " 
						+ df.format(melhores.get(0).getPrazo()/60) + "m");
		}
		else {
			String msgDialog = "";
			for(CalculoModel c : melhoresEmpate) {
				msgDialog = msgDialog + c.getNome() + "; Tipo de transporte: " + c.getNomeTipo()
					+ "; Custo: R$" + df.format(c.getCusto() + "; Prazo: "
						+ df.format(c.getPrazo()/60) + "m\n");
			}
			JOptionPane.showMessageDialog(null, "As melhores transportadoras para as condições informadas são:\n"
				+ msgDialog);
		}
	}

}
