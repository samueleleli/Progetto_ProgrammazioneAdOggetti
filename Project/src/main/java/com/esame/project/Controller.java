package com.esame.project;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public List Filtri(@RequestParam(value="TipoAttivita",required=false) String tipo
										, @RequestParam(value="Camere",required=false)Integer camere
										,@RequestParam(value="Municipio",required=false)Integer municipio 
										,@RequestParam(value="Map",required=false)boolean map) throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
, ParseException
	{
		// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		return FilterUtils.filtro("=",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio)
	}
	@RequestMapping(path="/data/filtro/{operazione}", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List FiltroMaggioreMinore(@RequestParam(value="TipoAttivita",required=false) String tipo
						,@RequestParam(value="Municipio",required=false)Integer municipio 
			            , @RequestParam(value="Camere",required=false)Integer camere
			            ,@RequestParam(value="Map",required=false)boolean map
						,@PathVariable("operazione") String operazione				) throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
, ParseException
	{
		// esempio di filtro: http://localhost:8080/data/filtro/maggiore?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		if(operazione.equals("maggiore"))    //solo per camere!!
		return FilterUtils.filtro(">",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio) 
		else if(operazione.equals("minore"))     //solo per camere!!
	    return FilterUtils.filtro("<",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio)
		else return null;
	}
	//stampa gli elementi unici e le occorrenze
	@RequestMapping(path="/data/stats", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public JSONObject Unici(@RequestParam(value="Field",required=false) String campo) throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
, ClassNotFoundException

	{
		Stats ElementiUnici = new Stats(campo);
		return ElementiUnici.getElementi() ;  //ritorna gli elementi unici e occorrenze
	}
	//stampa metadata in formato json
	@RequestMapping(path="/metadata", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		MetadataGenerator metadata = new MetadataGenerator();
		return metadata.getMetadata();
	}
	//stampa mappa in formato geojson
	@RequestMapping(path="/map", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONObject getMap () throws ParseException, IOException
	{   
		MapGenerator map = new MapGenerator();	
		return map.getMap();
	}

}
