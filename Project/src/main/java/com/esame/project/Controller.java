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

@RestController 
public class Controller {
    //stampa tutti i dati del dataset in formato json
	@RequestMapping(path="/data", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List<DatasetStructure> GetLista() throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
	{
		ListGenerator Lista = new ListGenerator();
		return Lista.getLista();
	}
	public @RequestMapping(path="/data", params={"filtro"})
	@ResponseBody String Getfiltriprova(@RequestParam("filtro") String search)
			 {
				return search;
			}
	@RequestMapping(path="/metadata", method = RequestMethod.GET, headers="Accept=application/json")
	//stampa metadata in formato json
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		MetadataGenerator metadata = new MetadataGenerator();
		return metadata.getMetadata();
	}
	@RequestMapping(path="/filtro", method = RequestMethod.GET, headers="Accept=application/json")
	//stampa risultati dei filtri applicati in formato json
	public @ResponseBody ArrayList<DatasetStructure> Getfiltri() throws IOException, ClassNotFoundException
	{
		ListGenerator lista = new ListGenerator();
		ArrayList<DatasetStructure> pout = lista.filterField("TipoAttivita", "=", "Affittacamere");//esempio di filtro
		return pout;	
	}
	@RequestMapping(path="/map", method = RequestMethod.GET, headers="Accept=application/json")
	//stampa mappa in formato geojson
	public JSONArray getMap () throws ParseException, IOException
	{   
		MapParsing map = new MapParsing();	
		return map.getMap();
	}

}
