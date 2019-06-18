package com.esame.project;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import org.json.simple.*;

@RestController 
public class Controller {
	//stampa tutti i dati del dataset in formato json
	@RequestMapping(path="/data", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List<DatasetStructure> GetAllData() throws FileNotFoundException, IOException 
	{
		ListGenerator Lista = new ListGenerator();
		return Lista.getLista();
	}
	//stampa i dati del filtro scelto
	@RequestMapping(path="/data/filtro", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List<DatasetStructure> Filtri(@RequestParam(value="TipoAttivita",required=false) String tipo
										, @RequestParam(value="Camere",required=false)Integer camere
										,@RequestParam(value="Municipio",required=false)Integer municipio) throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
	{
		// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		return FilterUtils.filtro(tipo,camere,municipio); //filtro per tipo di attività, numero di camere e zona(municipio)
	}
	//stampa metadata in formato json
	@RequestMapping(path="/metadata", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		MetadataGenerator metadata = new MetadataGenerator();
		return metadata.MetadataField();  //ottengo i dati tramite interfaccia
	}
	//stampa mappa in formato geojson
	@RequestMapping(path="/map", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONArray getMap () throws ParseException, IOException
	{   
		MapFilter map = new MapFilter();	
		return map.MapField();  //ottengo i dati tramite interfaccia
	}

}
