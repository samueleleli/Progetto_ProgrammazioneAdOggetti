package com.esame.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.*;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.esame.project.generator.GeojsonStruttureGenerator;
import com.esame.project.generator.ListaStruttureGenerator;
import com.esame.project.models.Metadata;
import com.esame.project.models.StrutturaAlberghiera;

/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che inizializza il server Spring.
 */
@SpringBootApplication
public class ProjectApplication {
	
	private static final String DATASET_NAME = "dataset.csv";
	private static final String MAPPA_NAME = "mappa.csv";
	private static final String URL="https://www.dati.gov.it/api/3/action/package_show?id=b3a787da-1ff7-4abd-a761-92154bbe2a9f"; //url JSON
	private static final String COMMA_DELIMITER = ";";
	public static ListaStruttureGenerator listGenerator;
	public static ArrayList<Metadata> listaMetadata = new ArrayList<Metadata>();
	public static GeojsonStruttureGenerator mapGenerator;
	private static final String CLASS_NAME = "com.esame.project.models.StrutturaAlberghiera";
	
	private static ArrayList<Metadata> getListaMetadata() throws Throwable {
		ArrayList<Metadata> metadata = new ArrayList<Metadata>();
		BufferedReader br = new BufferedReader(new FileReader(DATASET_NAME)); //file da leggere
		Class c = Class.forName(CLASS_NAME); //classe scelta
		Constructor listaCostruttori[] = c.getConstructors();   //ottiene lista dei costruttori
		Field listaParam[] = c.getDeclaredFields();				//ottiene lista degli attributi
		Class  tipiParam[] = listaCostruttori[0].getParameterTypes();  //ottengo i tipi degli attributi del costruttore
		String line = br.readLine(); 	//legge la prima riga del dataset in modo da ricavarne i titoli
		String[] valori = line.split(COMMA_DELIMITER,14); 
		//inserisco i dati raccolti in un array json
		for (int j=0; j < listaParam.length; j++)
		{   
			Field campoCorrente = listaParam[j];
			metadata.add(new Metadata(campoCorrente.getName(), valori[j],tipiParam[j].getName().replace("java.lang.String", "String")));   						//aggiungo di volta in volta l'oggetto all'Array json
		}
		return metadata;
	}
	
	private static void downloadDataset() throws IOException, ParseException
	{
		File dataset = new File(DATASET_NAME); 	//file che conterrà i dati del dataset
		File mappa = new File(MAPPA_NAME);		//file che conterrà i dati geojson
		URL connessione = new URL(URL); 		//crea nuova connessione utilizzando l'url
		String input;
		String dati="";
		System.setProperty("http.agent", "Chrome");
		URLConnection web = connessione.openConnection(); 	//apri connessione
		BufferedReader s = new BufferedReader(new InputStreamReader(web.getInputStream()));
		while ((input = s.readLine()) != null) 
		{
			dati+=input;   //prendi dati dal file
		}
		s.close();
		JSONObject ogg1 = (JSONObject) JSONValue.parseWithException(dati);	
		JSONObject ogg2 = (JSONObject) ogg1.get("result");   //ottiene l'oggetto result nel file json
		JSONArray ogg3 = (JSONArray) ogg2.get("resources");  //ottiene l'array resources contenuto in result
		for (Object test:ogg3)  //scorro l'array
		{
			if(test instanceof JSONObject)   //se l'elemento selezionato è di tipo JSONObject
			{
				JSONObject ogg4 = (JSONObject) test; 				//salvo i dati
				String formato = (String)ogg4.get("format");		//prendo il formato
				URL url1 = new URL ((String)ogg4.get("url"));	    //creo nuovo oggetto URL che conterrà l'url
				if(formato.equals("csv"))     
				{
					FileUtils.copyURLToFile(url1,dataset); //se formato è il csv lo scarico e inserisco il file in dataset
				}
				if(formato.equals("geojson")) 
				{
					FileUtils.copyURLToFile(url1,mappa); //se formato è geojson lo scarico e inserisco il file in mappa
				}
			}
		}
	}
	
	public static void main(String[] args) throws Throwable {
		//downloadDataset();
		listGenerator = new ListaStruttureGenerator();
		mapGenerator = new GeojsonStruttureGenerator(listGenerator.getLista());
		listaMetadata = getListaMetadata();
		SpringApplication.run(ProjectApplication.class, args);
	} 

	
}


