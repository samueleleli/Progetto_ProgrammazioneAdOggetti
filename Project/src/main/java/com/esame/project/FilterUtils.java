package com.esame.project;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce i filtri.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class FilterUtils<T> {
    /**
     * 
     * @param src lista completa
     * @param fieldName primo campo da confrontare
     * @param operator operatore
     * @param value secondo campo da confrontare
     * @return la lista filtrata 
     */
	public Collection<T> select(Collection<T> src, String fieldName, String operator, Object value) {
		Collection<T> out = new ArrayList<T>();
		for(T item:src) {
			try {
				Method m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				//ottiene il metodo
				try {
					Object tmp = m.invoke(item); //invoco il metodo
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
    /**
     * 
     * @param value primo valore da confrontare
     * @param operator operatore
     * @param th secondo valore da confrontare
     * @return true se sono uguali, maggiori o minori (a seconda dell'operazione scelta) e false se non lo sono
     */
	private static boolean check(Object value, String operator, Object th) {
		if (th instanceof Number && value instanceof Number) {	//se si stanno confrontando 2 numeri
			Double thC = ((Number)th).doubleValue();        //faccio il casting di tipo double
			Double valuec = ((Number)value).doubleValue();  //faccio il casting di tipo double
			if (operator.equals("="))				//se l'operatore è uguale
				return value.equals(th);  //ritorna vero se è maggiore sennò falso
			else if (operator.equals(">"))
				return valuec > thC;
			else if (operator.equals(">="))
					return valuec >= thC;
			else if (operator.equals("<="))
				return valuec <= thC;		
			else if (operator.equals("<"))
				return valuec < thC;
		}else if(th instanceof String && value instanceof String) //se si stanno confrontando 2 stringhe
			return value.equals(th);       //ritorna vero se sono uguali sennò falso
		return false;		//in tutti gli altri casi ritorna falso
	}
	//filtro dati
	/**
	 * 
	 * @param operator operatore scelto
	 * @param tipo tipo di struttura extra-alberghiera cercata
	 * @param camere numero di camere
	 * @param municipio numero del municipio
	 * @param map map che può valere true se si vuole stampare la lista in formato geojson, se vale false in formato json
	 * @return la lista filtrata
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static Object filtro (String operator,String tipo, Integer camere, Integer municipio, boolean map) throws FileNotFoundException, IOException, ParseException
	{
		//cambio i nomi per i filtri
		if (tipo != null)   
		{
			if(tipo.equals("BedeBreakfast")) tipo="Bed e Breakfast"; 
			if(tipo.equals("CasaFerie")) tipo="Casa Ferie";
			if(tipo.contentEquals("CasaUniversitaria")) tipo="casa universitaria bertoni";
		}
		//fine cambio i nomi per i filtri
		
		ListGenerator Lista =new ListGenerator();
		ArrayList filtroTipo = new ArrayList();
		ArrayList filtroCamere = new ArrayList();	
		ArrayList filtroMunicipio = new ArrayList(); 
		if(map==true)
		{
			JSONObject mappatura = new JSONObject();
			int n_camere;
			int n_municipio;
			String tipo_attivita;
			MapGenerator mappa = new MapGenerator();
			JSONObject datimappa = mappa.getMap();
			JSONArray features = (JSONArray) datimappa.get("features"); 
			JSONObject Camere = new JSONObject();
			JSONObject Tipo = new JSONObject();
			JSONObject Municipio = new JSONObject();
			for(Object supp:features)  //per ogni vettore o oggetto che scorro
			{ 
				if(supp instanceof JSONObject )  // valuto se è di tipo oggetto
				{     		
					JSONObject ogg = (JSONObject) supp;  //lo salvo
					JSONObject properties = (JSONObject) ogg.get("properties"); //ottengo l'oggetto properties
					JSONObject geometry = (JSONObject) ogg.get("geometry");
					if (camere!=null && properties.containsKey("camere")) 
					{
						n_camere = (int) properties.get("camere");
						boolean flagcamere = check(n_camere,operator,camere);
						if (flagcamere==true) filtroCamere.add(ogg);						
					}
					if (municipio!=null && properties.containsKey("MUNICIPIO")) 
					{
						n_municipio = (int) properties.get("MUNICIPIO"); 
						boolean flagmunicipio = check(municipio,"=",n_municipio);
						if (flagmunicipio) filtroMunicipio.add(ogg);
					}
					if (tipo!=null && properties.containsKey("tipo_attivita_ricettiva"))  
					{
						tipo_attivita = (String) properties.get("tipo_attivita_ricettiva"); 
						boolean flagtipo = check(tipo,"=",tipo_attivita);
						if (flagtipo==true) filtroTipo.add(ogg);
					}       
				}
			}
			mappatura.put("features",Listafiltrata(tipo, camere, municipio, filtroTipo, filtroCamere, filtroMunicipio));
			mappatura.put("type","FeatureCollection");
			return mappatura;
		}
		else
		{
			//cambio i nomi per cercare alcuni parametri
			if (tipo != null)   
			{
				if(tipo.equals("BedeBreakfast")) tipo="Bed e Breakfast"; 
				if(tipo.equals("CasaFerie")) tipo="Casa Ferie";
				if(tipo.contentEquals("CasaUniversitaria")) tipo="casa universitaria bertoni";
			}
			//fine cambio nomi

			filtroTipo = Lista.filterField("TipoAttivita", "=", tipo);  //filtro i tipi di attività
			filtroCamere = Lista.filterField("Camere", operator,camere);     //filtro i numeri delle camere
			filtroMunicipio = Lista.filterField("Municipio", "=",municipio); //filtro i municipi
			return Listafiltrata(tipo, camere, municipio, filtroTipo, filtroCamere, filtroMunicipio);
		}
	}
	/**
	 * 
	 * @param tipo tipo di struttura extra-alberghiera
	 * @param camere numero di camere
	 * @param municipio numero del municipio
	 * @param filtroTipo lista filtrata
	 * @param filtroCamere lista filtrata
	 * @param filtroMunicipio lista filtrata
	 * @return la lista filtrata
	 */
	private static List<DatasetStructure> Listafiltrata(String tipo, Integer camere, Integer municipio, List filtroTipo, List filtroCamere, List filtroMunicipio)
	{

		//valuto caso per caso il filtro da attuare a seconda della scelta dell'utente
		if(tipo!=null && camere==null && municipio==null) return filtroTipo;
		else if(tipo==null && camere!=null && municipio==null) return filtroCamere;
		else if(tipo==null && camere==null && municipio!=null) return filtroMunicipio;
		else if(tipo!=null || camere!=null || municipio!=null) 
		{
			//filtri multipli: in questo caso devo  prendere l'intersezione degli elementi ottenuti
			if(tipo!=null&&camere!=null&&municipio==null)  return intersezione(filtroTipo,filtroCamere);
			if(tipo!=null&&municipio!=null&&camere==null) return intersezione(filtroTipo,filtroMunicipio);
			if(tipo==null&&municipio!=null&&camere!=null) return intersezione(filtroMunicipio,filtroCamere);
			if(tipo!=null&&camere!=null&&municipio!=null)
			{
				List Intersezione=new ArrayList(filtroTipo);
				Intersezione.retainAll(filtroCamere);
				List Intersezione2=new ArrayList(filtroMunicipio);
				Intersezione2.retainAll(Intersezione);
				return Intersezione2;
			}
		}
		return null;
	}	
	/**
	 * 
	 * @param filtro1 prima lista filtrata
	 * @param filtro2 seconda lista filtrata
	 * @return intersezione tra filtro1 e filtro2
	 */
	private static List<DatasetStructure> intersezione(List filtro1,List filtro2) //intersezione tra le liste
	{
		List Intersezione=new ArrayList(filtro1);
		Intersezione.retainAll(filtro2);
		return Intersezione;
	}

}

