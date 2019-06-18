package com.esame.project;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FilterUtils<T> {
	
	public Collection<T> select(Collection<T> src, String fieldName, String operator, Object value) {
		Collection<T> out = new ArrayList<T>();
		for(T item:src) {
			try {
				Method m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				try {
					Object tmp = m.invoke(item);
					if(FilterUtils.check(tmp, operator, value))
						out.add(item);
				} catch (IllegalAccessException e) {
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
		return out;
	}
	
	public static boolean check(Object value, String operator, Object th) {
		if (th instanceof Number && value instanceof Number) {	//se si stanno confrontando 2 numeri
			Double thC = ((Number)th).doubleValue();        //faccio il casting di tipo double
			Double valuec = ((Number)value).doubleValue();  //faccio il casting di tipo double
			if (operator.equals("="))				//se l'operatore è uguale
				return value.equals(th);            //ritorna vero se è maggiore sennò falso
			else if (operator.equals(">"))          //se l'operatore è maggiore
				return valuec > thC;                //ritorna vero se è maggiore sennò falso
				else if (operator.equals("<"))       //se l'operatore è minore
					return valuec < thC;            //ritorna vero se è minore sennò falso
		}else if(th instanceof String && value instanceof String) //se si stanno confrontando 2 stringhe
			return value.equals(th);       //ritorna vero se sono uguali sennò falso
		return false;		//in tutti gli altri casi ritorna falso
	}
	
	public static List<DatasetStructure> filtro (String tipo, Integer camere, Integer municipio ) throws FileNotFoundException, IOException
	{
		ListGenerator Lista = new ListGenerator();
		ArrayList<DatasetStructure> filtroTipo = new ArrayList();
		ArrayList<DatasetStructure> filtroCamere = new ArrayList();
		ArrayList<DatasetStructure> filtroMunicipio = new ArrayList();
		//cambio i nomi per cercare alcuni parametri
		if (tipo != null)   
		{
			if(tipo.equals("BedeBreakfast")) tipo="Bed e Breakfast"; 
			if(tipo.equals("CasaFerie")) tipo="Casa Ferie";
		}
		//fine cambio nomi
		
		filtroTipo = Lista.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
		filtroCamere = Lista.filterField("Camere", "=",camere);     //filtro i numeri delle camere
		filtroMunicipio = Lista.filterField("Municipio", "=",municipio); //filtro i municipi
        
		//valuto caso per caso il filtro da attuare a seconda della scelta dell'utente
		if(tipo!=null && camere==null && municipio==null) return filtroTipo;
		else if(tipo==null && camere!=null && municipio==null) return filtroCamere;
		else if(tipo==null && camere==null && municipio!=null) return filtroMunicipio;
		else if(tipo!=null || camere!=null || municipio!=null) 
		{
			//filtri multipli: in questo caso devo  prendere l'intersezione degli elementi ottenuti
			if(tipo!=null&&camere!=null&&municipio==null)
			{
				List Intersezione=new ArrayList(filtroTipo);
				Intersezione.retainAll(filtroCamere);
				return Intersezione;
			}
			if(tipo!=null&&municipio!=null&&municipio==null)
			{
				List Intersezione=new ArrayList(filtroTipo);
				Intersezione.retainAll(filtroMunicipio);
				return Intersezione;
			}
			if(tipo!=null&&camere!=null&&municipio!=null)
			{
				List Intersezione=new ArrayList(filtroTipo);
				Intersezione.retainAll(filtroCamere);
				List Intersezione2=new ArrayList(filtroMunicipio);
				Intersezione2.retainAll(Intersezione);
				return Intersezione2;
			}
		}
	 return Lista.getLista(); // in tutti gli altri casi, ovvero quando il filtro 
	}

}
