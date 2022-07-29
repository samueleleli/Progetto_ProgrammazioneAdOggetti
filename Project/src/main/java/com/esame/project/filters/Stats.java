package com.esame.project.filters;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.esame.project.ProjectApplication;
import com.esame.project.models.Metadata;
import com.esame.project.models.StrutturaAlberghiera;

/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce le statistiche  ritorna i parametri presenti nel campo scelto e il numero di occorrenze per ognuno.
 */
public class Stats {
	private HashMap<String,Integer> statistiche; //oggetto JSON che contiene nome campo e numero di occorrenze 
    /**
     * 
     * @param fieldName campo di cui si vogliono trovare le occorrenze
     * @throws FileNotFoundException 
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Throwable 
     * @throws NoSuchMethodException 
     */
	public Stats(String fieldName) throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchMethodException, Throwable { 
		this.statistiche = new HashMap<String,Integer>();
		boolean flag=false;   //controllo che mi consente di salvare il campo nella lista se non compare tra i precedenti
		ArrayList<Metadata> metadataLista = ProjectApplication.listaMetadata;
		for(Metadata field : metadataLista)
		{
			if(field.getAlias().toLowerCase().equals(fieldName.toLowerCase()) && field.getType().equals("String"))  //controllo se il campo cercato è presente tra i metadati
			{
				flag=true; //se è presente, flag diventa true
				break;
			}
		}
		if(flag==true) //se flag diventa true dopo il controllo, ovvero il campo cercato è corretto
		{
			//Trovo numero di occorenze per il campo cercato
			flag=false;
			ArrayList<StrutturaAlberghiera> src = ProjectApplication.listGenerator.getLista();
			for(StrutturaAlberghiera item:src) {  //ciclo che scorre la lista completa
				flag=false; 
				Method m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				String dato = (String) m.invoke(item); 			//ottengo il campo che mi interessa nella richiesta
				this.statistiche.put(dato, this.statistiche.getOrDefault(dato,0)+1);
			}
		}
	}


	public HashMap<String,Integer> getElementi() {
		return statistiche;
	}


	public void setElementi(HashMap<String,Integer> elementi) {
		this.statistiche = elementi;
	}


}
