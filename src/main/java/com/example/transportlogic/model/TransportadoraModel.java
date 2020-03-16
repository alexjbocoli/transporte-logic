package com.example.transportlogic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Esta é a classe modelo da transportadora
 * 
 * @author Alex Juno Bócoli
 *
 */
@Entity
@Table(name="transportadora")
public class TransportadoraModel extends TipoTransporteModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	//private int id;
	@Column
	private String nome;
	@Column
	private double valor;
	@Column
	private double tempo;
	
	/**
	 * Construtor padrão
	 */
	public TransportadoraModel() {

	}
	
	/**
	 * Construtor principal
	 * @param idTipo o código do tipo de transporte
	 * @param nomeTipo o nome do tipo de transporte
	 * @param nome o nome da transportadora
	 * @param valor o valor por Km
	 * @param tempo o tempo por Km
	 */
	public TransportadoraModel(int idTipo, String nomeTipo, String nome, double valor, double tempo) {
		super(idTipo, nomeTipo);
		this.nome = nome;
		this.valor = valor;
		this.tempo = tempo;
	}

	/**
	 * Função para retornar o nome
	 * @return o nome da transportadora
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método para definir o nome
	 * @param nome o nome da transportadora
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Função para retornar o valor por Km
	 * @return o valor por Km
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Método para definir o valor por Km
	 * @param valor o valor por Km
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Função para retornar o tempo por Km
	 * @return o tempo por Km
	 */
	public double getTempo() {
		return tempo;
	}

	/**
	 * Método para definir o tempo por Km
	 * @param tempo o tempo por Km
	 */
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
}