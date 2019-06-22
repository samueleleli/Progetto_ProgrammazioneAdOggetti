package com.esame.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce le statistiche  ritorna i parametri presenti nel campo scelto e il numero di occorrenze per ognuno.
 */
public class Stats {
	private JSONObject Elementi = new JSONObject(); //oggetto JSON che contiene nome campo e numero di occorrenze 
    /**
     * 
     * @param fieldName campo di cui si vogliono trovare le occorrenze
     * @throws FileNotFoundException 
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public Stats(String fieldName) throws FileNotFoundException, IOException, ClassNotFoundException { 
		super();
		boolean flag=false;   //controllo che mi consente di salvare il campo nella lista se non compare tra i precedenti
		MetadataGenerator metadata =new MetadataGenerator();
		JSONArray array = metadata.getMetadata();
		for(int i=0;i<array.size();i++)
		{
			JSONObject app=(JSONObject) array.get(i);
			String tipo = (String) app.get("type");   //ottengo il tipo dai metadati
			String field = (String) app.get("alias"); //ottengo il nome della variabile
			if(field.equals(fieldName) && tipo.equals("String"))  //controllo se il campo cercato è presente tra i metadati
			{
				flag=true; //se è presente, flag diventa true
				break;
			}

		}
		if(flag==true) //se flag diventa true dopo il controllo, ovvero il campo cercato è corretto
		{
			//Trovo numero di occorenze per il campo cercato
			flag=false;
			ArrayList<String> ElementiUnici = new ArrayList<String>(); //lista di array che contiene gli elementi unici
			ElementiUnici.add("");   //aggiungo lo spazio vuoto per inizializzare la lista
			ListGenerator Lista = new ListGenerator(); //attributo di tipo ListGenerator così ottengo la lista completa
			List<DatasetStructure> src = Lista.getLista(); //ottengo la lista completa
			for(DatasetStructure item:src) {  //ciclo che scorre la lista completa
				flag=false; 
				try {
					Method m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
					//ottengo il metodo per estrapolare il campo
					try {
						String tmp = (String) m.invoke(item); 			//ottengo il campo che mi interessa nella richiesta
						for(int i=0;i<ElementiUnici.size();i++)  //scorro la lista 
						{
							if(tmp.equals(ElementiUnici.get(i))) flag=true; // se trovo un elemento uguale flag=true
						}
						if(flag==false) ElementiUnici.add(tmp);  //se flag rimane falso dopo aver scorso la lista lo aggiungo agli ElementiUNici

					}
					catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i<ElementiUnici.size();i++)
			{
				int cont=0; //contatore che contiene il numero di occorrenze per ogni campo
				for(DatasetStructure item2:src) {
					try {
						Method m = item2.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
						//Ottengo il metodo
						try {
							String tmp = (String) m.invoke(item2); //ottengo il campo
							if(tmp.equals(ElementiUnici.get(i))) cont++; //lo confronto con la lista degli elementi unici e incremento il contatore
						}
						catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Elementi.put(ElementiUnici.get(i),cont); //aggiungo i valori all'oggetto
			}
		}
		else
		{
			Elementi.put("result","refused - PARAMETRO INESISTENTE O DI TIPO NUMERICO"); //errore
		}

	}


	public JSONObject getElementi() {
		return Elementi;
	}


	public void setElementi(JSONObject elementi) {
		Elementi = elementi;
	}


}
