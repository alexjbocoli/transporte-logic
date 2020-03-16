package com.example.transportlogic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta é a classe modelo do cálculo
 * 
 * @author Alex Juno Bócoli
 *
 */
@Entity
@Table(name="calculo")
public class CalculoModel extends TipoTransporteModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	//private int idCalc;
	@Column
	private String nome;
	@Column
	private Double custo;
	@Column
	private Double prazo;
	
	/**
	 * Construtor padrão
	 */
	public CalculoModel() {
		
	}
	
	/**
	 * Construtor principal
	 * @param idTipo o código do tipo de transporte
	 * @param nomeTipo o nome do tipo de transporte
	 * @param nome o nome da transportadora
	 * @param custo o custo calculado de acordo com a distância
	 * @param prazo o prazo calculado de acordo com a distância
	 */
	public CalculoModel(int idTipo, String nomeTipo, String nome, double custo, double prazo) {
		super(idTipo, nomeTipo);
		this.nome = nome;
		this.custo = custo;
		this.prazo = prazo;
	}

	/**
	 * Função para retornar o nome
	 * @return o nome da transportadora do cálculo
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método para definir o nome
	 * @param nome o nome da transportadora do cálculo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Função para retornar o custo calculado
	 * @return o custo calculado para a transportadora
	 */
	public Double getCusto() {
		return custo;
	}

	/**
	 * Método para definir o custo calculado
	 * @param custo o custo calculado para a transportadora
	 */
	public void setCusto(Double custo) {
		this.custo = custo;
	}

	/**
	 * Função para retornar o prazo calculado
	 * @return o prazo calculado para a transportadora
	 */
	public Double getPrazo() {
		return prazo;
	}

	/**
	 * Método para definir o prazo calculado
	 * @param prazo o prazo calculado para a transportadora
	 */
	public void setPrazo(Double prazo) {
		this.prazo = prazo;
	}
}
