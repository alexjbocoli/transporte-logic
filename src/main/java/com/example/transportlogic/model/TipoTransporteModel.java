package com.example.transportlogic.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Esta é a classe modelo do tipo de transporte
 * 
 * @author Alex Juno Bócoli
 *
 */
@MappedSuperclass
public abstract class TipoTransporteModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idGeral;
	private int idTipo;
	private String nomeTipo;
	
	/**
	 * Construtor padrão
	 */
	public TipoTransporteModel() {
		
	}
	
	/**
	 * Construtor principal
	 * @param idTipo o código do tipo de transporte
	 * @param nomeTipo o nome do tipo de transporte
	 */
	public TipoTransporteModel(int idTipo, String nomeTipo) {
		this.idTipo = idTipo;
		this.nomeTipo = nomeTipo;
	}

	/**
	 * Função para retornar o código do tipo de transporte
	 * @return o código do tipo de transporte
	 */
	public int getIdTipo() {
		return idTipo;
	}

	/**
	 * Método para definir o código do tipo de transporte
	 * @param idTipo o código do tipo de transporte
	 */
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	/**
	 * Função para retornar o nome do tipo de transporte
	 * @return o nome do tipo de transporte
	 */
	public String getNomeTipo() {
		return nomeTipo;
	}

	/**
	 * Método para definir o nome do tipo de transporte
	 * @param nomeTipo o nome do tipo de transporte
	 */
	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
}
