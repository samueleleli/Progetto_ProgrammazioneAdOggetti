package com.esame.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce il file geojson.
 */
public class MapGenerator {

	private JSONObject map = new JSONObject(); 
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public MapGenerator() throws IOException, ParseException
	{

		// classe che stampa le posizioni delle strutture extra alberghiere in formato geojson in modo da poterle
		// inserire in una mappa vera come geojson.io
		// ho dovuto modificare il file tramite questa classe perchè alcune coordinate erano vuote
		// questo problema non permetteva la visulizzazione della mappa su geojson allora ho filtrato il file
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
				JSONObject geometry = (JSONObject) ogg3.get("geometry"); //prendo il contenuto di geometry
				JSONArray coordinates = (JSONArray) geometry.get("coordinates"); //e al suo interno quello delle coordinate
				if(coordinates.isEmpty())  continue; //se le coordinate sono vuote passo all'oggetto successiva
				//se invece il campo coordinates non è vuoto
				//modifico i valori in modo da averli uniformi per eventuali filtri
				JSONObject properties = (JSONObject) ogg3.get("properties");
				if (properties.containsKey("tipo_attivita_ricettiva"))
				{
					Object tipo=properties.get("tipo_attivita_ricettiva");
					if(tipo.equals("Case per ferie")) tipo="Case Ferie";
					else if(tipo.equals("case per ferie")) tipo="Case Ferie"; 
					else if(tipo.equals("Casa per ferie")) tipo="Case Ferie"; 
					else if(tipo.equals("Foresteria Lombarda/Locanda")) tipo="Foresteria";
					else if(tipo.equals("Ostello per la Gioventù")) tipo="Ostello";
					else if(tipo.equals("bed e breakfast")) tipo="Bed e Breakfast"; 
					properties.put("tipo_attivita_ricettiva", tipo);

				}
				if (properties.containsKey("camere"))
				{
					int camere = Integer.parseInt((String) properties.get("camere"));
					properties.put("camere", camere);
				}
				if (properties.containsKey("MUNICIPIO"))
				{
					int municipio = Integer.parseInt((String) properties.get("MUNICIPIO"));
					properties.put("MUNICIPIO", municipio);
				}			
				//fine controlli per filtro
				ogg3.put("properties", properties);
				app.add(ogg3);  			  //aggiungo l'oggetto al mio oggetto d'appoggio
			}
		
		}
		map.put("features",app); //tutti i dati  che ho ottenuto li inserisco in un nuovo oggetto
		map.put("type","FeatureCollection"); //aggiungo un parametro che viene utilizzato come standard nei file geojson
		
		
	}
	public JSONObject getMap() {
		return map;
	}
	public void setMap(JSONObject map) {
		this.map = map;
	}

}


