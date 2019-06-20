package com.esame.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws IOException, ParseException {
		DownloadDataset();
		SpringApplication.run(ProjectApplication.class, args);
	} 


	private static void DownloadDataset() throws IOException, ParseException
	{
		File dataset = new File("dataset.csv"); 	//file che conterrà i dati del dataset
		File mappa = new File("mappa.geojson");		//file che conterrà i dati geojson
		String input;
		String dati="";
		String url="https://www.dati.gov.it/api/3/action/package_show?id=b3a787da-1ff7-4abd-a761-92154bbe2a9f"; //url JSON
		URL connessione = new URL(url); 					//crea nuova connessione utilizzando l'url
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
				JSONObject Ogg4 = (JSONObject) test; 				//salvo i dati
				String formato = (String)Ogg4.get("format");		//prendo il formato
				URL url1 = new URL ((String)Ogg4.get("url"));	    //creo nuovo oggetto URL che conterrà l'url
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
}

