package com.example.transportlogic;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta é a classe principal da aplicação
 * 
 * @author Alex Juno Bócoli
 *
 */
@SpringBootApplication
public class TransporteLogicApplication {

	/**
	 * Método principal da aplicação
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TransporteLogicApplication.class, args);
		
		System.setProperty("java.awt.headless", "false");
		
		JFrame janela = new JFrame();
		janela.setLayout(new GridLayout(3,1));
		janela.setTitle("Automação de fretes Cardboard Box Company");
		janela.setSize(600, 200);
		janela.setLocation(400, 200);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel painel1 = new JPanel();
		JPanel painel2 = new JPanel();
		JPanel painel3 = new JPanel();
		painel1.setLayout(new FlowLayout());
		painel2.setLayout(new FlowLayout());
		painel3.setLayout(new FlowLayout());
		janela.add(painel1);
		janela.add(painel2);
		janela.add(painel3);
		
		JLabel label1 = new JLabel("Origem: ");
		JLabel label2 = new JLabel("Destino: ");
		JLabel label3 = new JLabel("Distância (Km): ");
		JLabel label4 = new JLabel("Tipo de transporte: ");
		JLabel label5 = new JLabel("Prioridade: ");
		
		JTextField edit1 = new JTextField(10); // Origem
		JTextField edit2 = new JTextField(10); // Destino
		JTextField edit3 = new JTextField(10); // Distância
		
		JComboBox<String> box1 = new JComboBox<String>(); // Tipo de transporte
		box1.addItem("0=Ambos");
		box1.addItem("1=Terrestre");
		box1.addItem("2=Aéreo");
		
		JComboBox<String> box2 = new JComboBox<String>(); // Prioridade
		box2.addItem("0=Menor custo");
		box2.addItem("1=Menor prazo");
		
		JButton btCalcular = new JButton("Calcular");
		JButton btTeste = new JButton("Teste");
		BotaoCalcular actionCalc = new BotaoCalcular(edit1, edit2, edit3, box1, box2);
		BotaoTeste actionTest = new BotaoTeste(edit1, edit2, edit3, box1, box2);
		btCalcular.addActionListener(actionCalc);
		btTeste.addActionListener(actionTest);
		
		painel1.add(label1);
		painel1.add(edit1);
		painel1.add(label2);
		painel1.add(edit2);
		painel1.add(label3);
		painel1.add(edit3);
		painel2.add(label4);
		painel2.add(box1);
		painel2.add(label5);
		painel2.add(box2);
		painel3.add(btTeste);
		painel3.add(btCalcular);
		
		janela.setVisible(true);
	}
}
