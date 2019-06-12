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

public class MapParsing {
	private JSONArray map = new JSONArray(); 
	public MapParsing() throws IOException, ParseException
	{
		String dati="";
		BufferedReader br = new BufferedReader(new FileReader("mappa.geojson"));
		String line;
		while ((line = br.readLine()) != null) //legge ogni riga fino a quando non è arrivato alla fine del file 
		{ 
			dati+=line;
		}
		br.close();
		JSONArray app = new JSONArray();
		JSONObject app2 = new JSONObject();
		JSONObject ogg1 = (JSONObject) JSONValue.parseWithException(dati);	
		JSONArray ogg2 = (JSONArray) ogg1.get("features");

		for(Object supp:ogg2)
		{
			if(supp instanceof JSONObject )
			{
				JSONObject ogg3 = (JSONObject) supp;
				JSONObject ogg4 = (JSONObject) ogg3.get("geometry");
				JSONArray ogg5 = (JSONArray) ogg4.get("coordinates");
				if(ogg5.isEmpty()) 
				{ 
					continue; 
				}
				app.add(ogg3);
			}


		}
		app2.put("features",app);
		app2.put("type","FeatureCollection");
		map.add(app2);

	}
	public JSONArray getMap() {
		return map;
	}
	public void setMap(JSONArray map) {
		this.map = map;
	}

}
