package com.esame.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.json.simple.*;

public class MapFilter {
	private JSONArray map = new JSONArray(); 
	public MapFilter() throws IOException, ParseException
	{
		
		// classe che stampa le posizioni delle strutture extra alberghiere in formato geojson in modo da poterle
		// inserire in una mappa vera come geojson.io
		// ho dovuto modificare il file tramite questa classe perchè alcune coordinate erano vuote
		// questo problema non permetteva la visulizzazione della mappa su geojson allora ho il filtrato il file
		// in modo da eliminare questi elementi
		
		String dati=""; 
		BufferedReader br = new BufferedReader(new FileReader("mappa.geojson")); //file da leggere
		String line;
		while ((line = br.readLine()) != null) //legge ogni riga fino a quando non è arrivato alla fine del file 
		{ 
			dati+=line;
		}
		br.close();
		
		JSONArray app = new JSONArray();  
		JSONObject app2 = new JSONObject();
		JSONObject ogg1 = (JSONObject) JSONValue.parseWithException(dati);	
		JSONArray ogg2 = (JSONArray) ogg1.get("features"); //ottengo l'oggetto features

		for(Object supp:ogg2)  //per ogni vettore o oggetto che scorro
		{ 
			if(supp instanceof JSONObject )  // valuto se è di tipo oggetto
			{                                // e se lo è
				JSONObject ogg3 = (JSONObject) supp;  //lo salvo
				JSONObject ogg4 = (JSONObject) ogg3.get("geometry"); //prendo il contenuto di geometry
				JSONArray ogg5 = (JSONArray) ogg4.get("coordinates"); //e al suo interno quello delle coordinate
				if(ogg5.isEmpty())  continue; //se le coordinate sono vuote passo all'iterazione successiva
				app.add(ogg3);  //sennò aggiungo l'oggetto al mio oggetto d'appoggio
			}
		}
		app2.put("features",app); //tutti i dati  che ho ottenuto li inserisco in un nuovo oggetto
		app2.put("type","FeatureCollection"); //aggiungo un parametro che viene utilizzato come standard nei file geojson
		map.add(app2);  //inserisco il tutto in una lista che poi verrà visualizzata

	}
	public JSONArray getMap() {
		return map;
	}
	public void setMap(JSONArray map) {
		this.map = map;
	}
	public JSONArray MapField() {
		return (JSONArray) this.getMap();
	}

}
