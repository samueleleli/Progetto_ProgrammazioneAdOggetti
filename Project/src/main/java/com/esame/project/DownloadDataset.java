package com.esame.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class DownloadDataset {	
	public DownloadDataset() throws IOException, ParseException
	{
		File dataset = new File("dataset.csv");
		File mappa = new File("mappa.geojson");
		String input;
		String dati="";
		String url="https://www.dati.gov.it/api/3/action/package_show?id=b3a787da-1ff7-4abd-a761-92154bbe2a9f";
		URL connessione = new URL(url);
		System.setProperty("http.agent", "Chrome");
		URLConnection web = connessione.openConnection();
		BufferedReader s = new BufferedReader(new InputStreamReader(web.getInputStream()));
		while ((input = s.readLine()) != null) 
		{
			dati+=input;
		}
		s.close();
		JSONObject ogg1 = (JSONObject) JSONValue.parseWithException(dati);	
		JSONObject ogg2 = (JSONObject) ogg1.get("result");
		JSONArray ogg3 = (JSONArray) ogg2.get("resources");
		for (Object test:ogg3)
		{
			if(test instanceof JSONObject)
			{
				JSONObject Ogg4 = (JSONObject) test; 				
				String formato = (String)Ogg4.get("format");		
				URL url1 = new URL ((String)Ogg4.get("url"));	
				if(formato.equals("csv")) 
				{
					FileUtils.copyURLToFile(url1,dataset);
				}
				if(formato.equals("geojson")) 
				{
					FileUtils.copyURLToFile(url1,mappa);
				}
			}
		}
	}
}

