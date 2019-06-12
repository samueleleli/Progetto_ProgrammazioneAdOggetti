package com.esame.project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class MetadataGenerator {

	private static final String COMMA_DELIMITER = ";";
	private JSONArray metadata=new JSONArray();
	public MetadataGenerator() throws ClassNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv")); //file da leggere
		Class c = Class.forName("com.esame.project.DatasetStructure"); //classe scelta
		Constructor listaCostruttori[] = c.getConstructors();  //ottiene lista dei costruttori
		Field listaParam[] = c.getDeclaredFields();			//ottiene lista degli attributi

		Class  tipiParam[] = listaCostruttori[0].getParameterTypes();  //ottengo i tipi degli attributi del costruttore
		String line;
		line = br.readLine(); 	//legge la prima riga del dataset in modo da ricavarne i titoli
		String[] valori = line.split(COMMA_DELIMITER,14); 
		//inserisco i dati raccolti in un array json
		for (int j=0; j < listaParam.length; j++)
		{   
			JSONObject obj = new JSONObject();
			Field campoCorrente = listaParam[j];
			//crea un oggetto con i parametri alias type e nome a cui andiamo ad aggiungere i valori presi
			obj.put("alias", campoCorrente.getName());
			obj.put("sourceField", valori[j] );
			obj.put("type", tipiParam[j].getName());
			String tipo=(String) obj.get("type");  		//ottiene il tipo dell'oggetto in modo da contollarlo
			if(tipo.equals("java.lang.String")) obj.put("type","String");   //se l'oggetto è java.lang.String lo cambio in String
			metadata.add(obj);   						//aggiungo di volta in volta l'oggetto all'Array json
		}
	}   
	public JSONArray getMetadata() {
		return metadata;
	}
	public void setMetadata(JSONArray metadata) {
		this.metadata = metadata;
	}




}
