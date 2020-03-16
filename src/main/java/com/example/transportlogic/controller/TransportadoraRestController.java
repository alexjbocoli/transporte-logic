package com.example.transportlogic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.transportlogic.model.TransportadoraModel;

/**
 * Esta é a classe que controla o WebService Rest
 * 
 * @author Alex Juno Bócoli
 *
 */
@RestController
public class TransportadoraRestController {
	private Map<Integer, TransportadoraModel> transportadorasRest;
	private Map<Integer, TransportadoraModel> transportadorasRestTipo;
	
	/**
	 * Construtor da classe
	 */
	public TransportadoraRestController() {
		transportadorasRest = new HashMap<Integer, TransportadoraModel>();
		
		TransportadoraModel t2a = new TransportadoraModel(1, "Terrestre", "Transportadora 2", 75, 59);
		TransportadoraModel t2b = new TransportadoraModel(2, "Aérea", "Transportadora 2", 200, 30);
		TransportadoraModel t3a = new TransportadoraModel(1, "Terrestre", "Transportadora 3", 55, 65);
		TransportadoraModel t3b = new TransportadoraModel(2, "Aérea", "Transportadora 3", 180, 33);
		TransportadoraModel t4 = new TransportadoraModel(2, "Aérea", "Transportadora 4", 175, 30);
		transportadorasRest.put(1, t2a);
		transportadorasRest.put(2, t2b);
		transportadorasRest.put(3, t3a);
		transportadorasRest.put(4, t3b);
		transportadorasRest.put(5, t4);
	}
	
	/**
	 * Função responsável por listar as transportadoras
	 * @return uma resposta html de sucesso
	 */
	@RequestMapping(value = "/service/transportadora", method = RequestMethod.GET)
	public ResponseEntity<List<TransportadoraModel>> listar() {
		return new ResponseEntity<List<TransportadoraModel>>(new ArrayList<TransportadoraModel>(transportadorasRest.values()), HttpStatus.OK);
	}
	
	/**
	 * Função responsável por listar as transportadoras de acordo com o tipo de transporte
	 * @param tipoTransporte o tipo de transporte que se deseja listar
	 * @return uma resposta html de sucesso ou não encontrado
	 */
	@RequestMapping(value = "/service/transportadora/tipoTransporte={tipoTransporte}", method = RequestMethod.GET)
	public ResponseEntity<List<TransportadoraModel>> listarTipoTransporte(@PathVariable("tipoTransporte") Integer tipoTransporte) {
		int j = 1;
		transportadorasRestTipo = new HashMap<Integer, TransportadoraModel>();
		
		for(int i = 1; i <= transportadorasRest.size(); i++) {			
			if(transportadorasRest.get(i).getIdTipo() == tipoTransporte) {
				transportadorasRestTipo.put(j, transportadorasRest.get(i));
				j++;
			}
		}
	 
		if (transportadorasRestTipo.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	 
		return new ResponseEntity<List<TransportadoraModel>>(new ArrayList<TransportadoraModel>(transportadorasRestTipo.values()), HttpStatus.OK);
	}
}
