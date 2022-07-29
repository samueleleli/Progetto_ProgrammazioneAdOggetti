package com.esame.project.generator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

import com.esame.project.ProjectApplication;
import com.esame.project.filters.FilterUtils;
import com.esame.project.filters.IFilter;
import com.esame.project.models.MapElement;
import com.esame.project.models.StrutturaAlberghiera;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che gestisce il file geojson.
 */
public class GeojsonStruttureGenerator implements IFilter<StrutturaAlberghiera, Object>  {

	private HashMap<String,Object> map = new HashMap<String,Object>();
	private FilterUtils<StrutturaAlberghiera> utils;
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public GeojsonStruttureGenerator(ArrayList<StrutturaAlberghiera> listaStrutture) throws IOException, ParseException
	{
		// classe che stampa le posizioni delle strutture extra alberghiere in formato geojson in modo da poterle
		// inserire in una mappa vera come geojson.io
		ArrayList<MapElement> listaFeature = new ArrayList<MapElement>();
		for(StrutturaAlberghiera struttura : listaStrutture) {
			double[] coord = new double[2];
			coord[0] = struttura.getLongit();
			coord[1] = struttura.getLatit();
			listaFeature.add(new MapElement(coord,struttura));
		}
		this.map.put("features",listaFeature);
		this.map.put("type","FeatureCollection");
		this.utils = new FilterUtils<StrutturaAlberghiera>();	
	}
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	
	@Override
	public ArrayList<StrutturaAlberghiera> filterField(String fieldName, String operator, Object value) {
			try {
				if (value != null)   
				{
					if(value.equals("BedeBreakfast")) value="Bed e Breakfast"; 
					if(value.equals("CasaFerie")) value="Casa Ferie";
					if(value.equals("CasaUniversitaria")) value="casa universitaria bertoni";
				}
				ArrayList<StrutturaAlberghiera> struttureAlberghiere =  new ArrayList<StrutturaAlberghiera>();
				ArrayList<MapElement> listaFeature =  (ArrayList<MapElement>) this.getMap().get("features");
				for(MapElement map:listaFeature) struttureAlberghiere.add(map.getProperties());
					
				return (ArrayList<StrutturaAlberghiera>) utils.select(struttureAlberghiere, fieldName, operator, value);
			} catch (NoSuchMethodException e) {
				return new ArrayList<StrutturaAlberghiera>();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				return new ArrayList<StrutturaAlberghiera>();
			}
	}

}


