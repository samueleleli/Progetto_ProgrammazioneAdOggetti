package com.esame.project.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.*;

import com.esame.project.ProjectApplication;
import com.esame.project.filters.FilterUtils;
import com.esame.project.filters.Stats;
import com.esame.project.generator.GeojsonStruttureGenerator;
import com.esame.project.models.MapElement;
import com.esame.project.models.Metadata;
import com.esame.project.models.StrutturaAlberghiera;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce le richieste GET.
 */
@RestController 
public class Controller {
	//invia tutti i dati del dataset in formato json
	/**
	 * 
	 * @return lista completa
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(path="/data", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public List<StrutturaAlberghiera> getAllData() throws FileNotFoundException, IOException 
	{	
		return ProjectApplication.listGenerator.getLista();
	}
	
	//invia metadata in formato json
		/**
		 * 
		 * @return i metadati
		 * @throws FileNotFoundException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		@RequestMapping(path="/metadata", method = RequestMethod.GET, headers="Accept=application/json")
		public ArrayList<Metadata> GetMetadata() throws FileNotFoundException, IOException, ClassNotFoundException
		{
			return ProjectApplication.listaMetadata;
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
	public ArrayList<StrutturaAlberghiera> applyFilters(@RequestParam(value="TipoAttivita",required=false) String tipo
										, @RequestParam(value="Camere",required=false)Integer camere
										,@RequestParam(value="Municipio",required=false)Integer municipio 
										,@RequestParam(value="Map",required=false)boolean map) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json
    {
		// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		//fine cambio i nomi per i filtri
		ArrayList<StrutturaAlberghiera> filtro = new ArrayList<StrutturaAlberghiera>();
		ArrayList<StrutturaAlberghiera> filtroCamere = new ArrayList<StrutturaAlberghiera>();	
		ArrayList<StrutturaAlberghiera> filtroMunicipio = new ArrayList<StrutturaAlberghiera>(); 
		
		filtro = ProjectApplication.listGenerator.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
		filtroCamere = ProjectApplication.listGenerator.filterField("Camere", "=",camere);     //filtro i numeri delle camere
		filtroMunicipio = ProjectApplication.listGenerator.filterField("Municipio", "=",municipio); //filtro i municipi	
		
		filtro.retainAll(filtroCamere);
		filtro.retainAll(filtroMunicipio);
		return filtro;
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
	public ArrayList<StrutturaAlberghiera> FiltroMaggioreMinore(@RequestParam(value="TipoAttivita",required=false) String tipo
						,@RequestParam(value="Municipio",required=false)Integer municipio 
			            , @RequestParam(value="Camere",required=false)Integer camere
						,@PathVariable("operazione") String operazione) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json

	{
		// esempio di filtro: http://localhost:8080/data/filtro/maggiore?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		String symbol;
		if(operazione.equals("maggiore"))  symbol = ">";
		else if(operazione.equals("minore"))   symbol = "<";
		else if(operazione.equals("maggioreuguale")) symbol = ">=";
		else if(operazione.equals("minoreuguale")) symbol = "<=";
		else return null;
		// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
		//fine cambio i nomi per i filtri
		ArrayList<StrutturaAlberghiera> filtro = new ArrayList<StrutturaAlberghiera>();
		ArrayList<StrutturaAlberghiera> filtroCamere = new ArrayList<StrutturaAlberghiera>();	
		ArrayList<StrutturaAlberghiera> filtroMunicipio = new ArrayList<StrutturaAlberghiera>(); 
				
		filtro = ProjectApplication.listGenerator.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
		filtroCamere = ProjectApplication.listGenerator.filterField("Camere", symbol ,camere);     //filtro i numeri delle camere
		filtroMunicipio = ProjectApplication.listGenerator.filterField("Municipio", "=" ,municipio); //filtro i municipi	
				
		filtro.retainAll(filtroCamere);
		filtro.retainAll(filtroMunicipio);
		return filtro;
	}
	/**
	 * 
	 * @param campo Campo di cui si vogliono trovare gli elementi unici
	 * @return gli elementi unici e il numero di occorrenze
	 * @throws Throwable 
	 * @throws NoSuchMethodException 
	 */
	//stampa gli elementi unici e le occorrenze
	@RequestMapping(path="/data/stats", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
	public HashMap<String, Integer> Unici(@RequestParam(value="field",required=false) String campo) throws NoSuchMethodException, Throwable

	{
		Stats elementiUnici = new Stats(campo);
		return elementiUnici.getElementi() ;  //ritorna gli elementi unici e occorrenze
	}
	
	//stampa dati in formato geojson
	/**
	 * 
	 * @return 
	 * @return i dati in formato geojson
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(path="/map", method = RequestMethod.GET, headers="Accept=application/json")

     public HashMap<String,Object> jsonMap() throws IOException, ParseException {
			return ProjectApplication.mapGenerator.getMap();
	}
	
	//stampa i dati del filtro scelto
		@RequestMapping(path="/map/filtro", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
		public HashMap<String,Object> applyGeojsonFilters(@RequestParam(value="TipoAttivita",required=false) String tipo
											, @RequestParam(value="Camere",required=false)Integer camere
											,@RequestParam(value="Municipio",required=false)Integer municipio) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json
	    {
			// esempio di filtro: http://localhost:8080/map/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
			ArrayList<StrutturaAlberghiera> filtro = new ArrayList<StrutturaAlberghiera>();
			ArrayList<StrutturaAlberghiera> filtroCamere = new ArrayList<StrutturaAlberghiera>();	
			ArrayList<StrutturaAlberghiera> filtroMunicipio = new ArrayList<StrutturaAlberghiera>();  
			
			filtro = ProjectApplication.mapGenerator.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
			filtroCamere = ProjectApplication.mapGenerator.filterField("Camere", "=",camere);     //filtro i numeri delle camere
			filtroMunicipio = ProjectApplication.mapGenerator.filterField("Municipio", "=",municipio); //filtro i municipi	
			
			filtro.retainAll(filtroCamere);
			filtro.retainAll(filtroMunicipio);
			
			GeojsonStruttureGenerator geoJson = new GeojsonStruttureGenerator(filtro);
			
			return geoJson.getMap();
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
		@RequestMapping(path="/map/filtro/{operazione}", method = RequestMethod.GET, headers="Accept=application/json; charset=utf-8")
		public HashMap<String,Object> FiltroGeojsonFiltersMaggioreMinore(@RequestParam(value="TipoAttivita",required=false) String tipo
							,@RequestParam(value="Municipio",required=false)Integer municipio 
				            , @RequestParam(value="Camere",required=false)Integer camere
							,@PathVariable("operazione") String operazione) throws FileNotFoundException, IOException, ParseException //stampa tutti i dati del dataset in formato json

		{
			String symbol;
			if(operazione.equals("maggiore"))  symbol = ">";
			else if(operazione.equals("minore"))   symbol = "<";
			else if(operazione.equals("maggioreuguale")) symbol = ">=";
			else if(operazione.equals("minoreuguale")) symbol = "<=";
			else return null;
			// esempio di filtro: http://localhost:8080/data/filtro?TipoAttivita=Affittacamere&Camere=2&Municipio=1
			//fine cambio i nomi per i filtri
			ArrayList<StrutturaAlberghiera> filtro = new ArrayList<StrutturaAlberghiera>();
			ArrayList<StrutturaAlberghiera> filtroCamere = new ArrayList<StrutturaAlberghiera>();	
			ArrayList<StrutturaAlberghiera> filtroMunicipio = new ArrayList<StrutturaAlberghiera>(); 
					
			filtro = ProjectApplication.mapGenerator.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
			filtroCamere = ProjectApplication.mapGenerator.filterField("Camere", symbol ,camere);     //filtro i numeri delle camere
			filtroMunicipio = ProjectApplication.mapGenerator.filterField("Municipio", "=" ,municipio); //filtro i municipi	
					
			filtro.retainAll(filtroCamere);
			filtro.retainAll(filtroMunicipio);
			
			GeojsonStruttureGenerator geoJson = new GeojsonStruttureGenerator(filtro);
			
			return geoJson.getMap();
		}
		
}
