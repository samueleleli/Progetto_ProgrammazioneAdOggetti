package com.esame.project.filters;
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

import com.esame.project.ProjectApplication;
import com.esame.project.models.StrutturaAlberghiera;

public class FilterUtils<T> {
    /**
     * 
     * @param src lista completa
     * @param fieldName primo campo da confrontare
     * @param operator operatore
     * @param value secondo campo da confrontare
     * @return la lista filtrata 
     * @throws Throwable 
     * @throws NoSuchMethodException 
     */
	public Collection<T> select(Collection<T> src, String fieldName, String operator, Object value) throws NoSuchMethodException, Throwable {
		Collection<T> out = new ArrayList<T>();
		for(T item:src) {
				Method m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
				//ottiene il metodo
				Object tmp = m.invoke(item); //invoco il metodo
				if(FilterUtils.check(tmp, operator, value)) 
					out.add(item);		
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
	

	

}

