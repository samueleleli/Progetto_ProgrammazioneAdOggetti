package com.esame.project.models;

import java.util.HashMap;

public class MapElement {
	private final String type = "Feature";
	private HashMap<String,Object> geometry;
	private StrutturaAlberghiera properties;
	
	public MapElement(double[] coordinates,StrutturaAlberghiera struttura){
		this.geometry = new HashMap<String,Object>();
		this.geometry.put("coordinates",coordinates);
		this.geometry.put("type","Point");
		this.properties = struttura;
	}

	public HashMap<String, Object> getGeometry() {
		return geometry;
	}

	public void setGeometry(HashMap<String, Object> geometry) {
		this.geometry = geometry;
	}

	public StrutturaAlberghiera getProperties() {
		return properties;
	}

	public void setProperties(StrutturaAlberghiera properties) {
		this.properties = properties;
	}

	public String getType() {
		return type;
	}
	

}
