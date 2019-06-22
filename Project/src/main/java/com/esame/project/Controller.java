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
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce le richieste GET.
 */
@RestController 
public class Controller {
	//stampa tutti i dati del dataset in formato json
	/**
	 * 
	 * @return lista completa
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(path="/data", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List<DatasetStructure> GetAllData() throws FileNotFoundException, IOException 
	{
		ListGenerator Lista = new ListGenerator();
		return Lista.getLista();
	}
	/**
	 * 
	 * @param tipo tipo di struttura extra-alberghiera
	 * @param camere numero di camere
	 * @param municipio numero del municipio
	 * @param map indica se si vuole ottenere il file in formato geojson
	 * @return lista filtrata
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	//stampa i dati del filtro scelto
	@RequestMapping(path="/data/filtro", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List Filtri(@RequestParam(value="TipoAttivita",required=false) String tipo
										, @RequestParam(value="Camere",required=false)Integer camere
										,@RequestParam(value="Municipio",required=false)Integer municipio 
										,@RequestParam(value="Map",required=false)boolean map) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json
    {
		// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		return FilterUtils.filtro("=",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio)
	}

	 /** 
	 * @param tipo tipo di struttura extra-alberghiera
	 * @param camere numero di camere
	 * @param municipio numero del municipio
	 * @param map indica se si vuole ottenere il file in formato geojson
	 * @param operazione indica il parametro operazione maggiore o minore
	 * @return lista filtrata
     * @return lista filtrata
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(path="/data/filtro/{operazione}", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List FiltroMaggioreMinore(@RequestParam(value="TipoAttivita",required=false) String tipo
						,@RequestParam(value="Municipio",required=false)Integer municipio 
			            , @RequestParam(value="Camere",required=false)Integer camere
			            ,@RequestParam(value="Map",required=false)boolean map
						,@PathVariable("operazione") String operazione) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json

	{
		// esempio di filtro: http://localhost:8080/data/filtro/maggiore?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		if(operazione.equals("maggiore"))    //solo per camere!!
		return FilterUtils.filtro(">",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio) 
		else if(operazione.equals("minore"))     //solo per camere!!
	    return FilterUtils.filtro("<",tipo,camere,municipio,map); //filtro per tipo di attività, numero di camere e zona(municipio)
		else return null;
	}
	/**
	 * 
	 * @param campo Campo di cui si vogliono trovare gli elementi unici
	 * @return gli elementi unici e il numero di occorrenze
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//stampa gli elementi unici e le occorrenze
	@RequestMapping(path="/data/stats", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public JSONObject Unici(@RequestParam(value="Field",required=false) String campo) throws FileNotFoundException, IOException //stampa tutti i dati del dataset in formato json
, ClassNotFoundException

	{
		Stats ElementiUnici = new Stats(campo);
		return ElementiUnici.getElementi() ;  //ritorna gli elementi unici e occorrenze
	}
	//stampa metadata in formato json
	/**
	 * 
	 * @return i metadati
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(path="/metadata", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONArray GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		MetadataGenerator metadata = new MetadataGenerator();
		return metadata.getMetadata();
	}
	//stampa dati in formato geojson
	/**
	 * 
	 * @return i dati in formato geojson
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(path="/map", method = RequestMethod.GET, headers="Accept=application/json")
	public JSONObject getMap () throws ParseException, IOException
	{   
		MapGenerator map = new MapGenerator();	
		return map.getMap();
	}

}
