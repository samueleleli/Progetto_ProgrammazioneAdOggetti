package com.esame.project;

import java.util.ArrayList;
//classe che contiene i parametri per contenere la struttura del dataset

public class DatasetStructure  {

	String Codice;
	String Ubicazione;
	String AreaCompetenza;
	String Via;
	String Civico;
	int CodiceVia;
	int Camere;
	String CamerePiano;
	String Insegna;
	String TipoAttivita;
	int Municipio;
	double Longit;
	double Latit;
	String Location;
    //classe che contiene i parametri del dataset
	public DatasetStructure(String codice, String ubicazione, String areaCompetenza, String via, String civico, int codiceVia,
			int camere, String camerePiano, String insegna, String tipoAttivita, int municipio, double longit,
			double latit, String location) {
		super();
		Codice = codice;
		Ubicazione = ubicazione;
		AreaCompetenza = areaCompetenza;
		Via = via;
		Civico = civico;
		CodiceVia = codiceVia;
		Camere = camere;
		CamerePiano = camerePiano;
		Insegna = insegna;
		TipoAttivita = tipoAttivita;
		Municipio = municipio;
		Longit = longit;
		Latit = latit;
		Location = location;
	}

	public String getCodice() {
		return Codice;
	}
	public void setCodice(String codice) {
		Codice = codice;
	}
	public String getUbicazione() {
		return Ubicazione;
	}
	public void setUbicazione(String ubicazione) {
		Ubicazione = ubicazione;
	}
	public String getAreaCompetenza() {
		return AreaCompetenza;
	}
	public void setAreaCompetenza(String areaCompetenza) {
		AreaCompetenza = areaCompetenza;
	}
	public String getVia() {
		return Via;
	}
	public void setVia(String via) {
		Via = via;
	}
	public String getCivico() {
		return Civico;
	}
	public void setCivico(String civico) {
		Civico = civico;
	}
	public int getCodiceVia() {
		return CodiceVia;
	}
	public void setCodiceVia(int codiceVia) {
		CodiceVia = codiceVia;
	}
	public int getCamere() {
		return Camere;
	}
	public void setCamere(int camere) {
		Camere = camere;
	}
	public String getCamerePiano() {
		return CamerePiano;
	}
	public void setCamerePiano(String camerePiano) {
		CamerePiano = camerePiano;
	}
	public String getInsegna() {
		return Insegna;
	}
	public void setInsegna(String insegna) {
		Insegna = insegna;
	}
	public String getTipoAttivita() {
		return TipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		TipoAttivita = tipoAttivita;
	}
	public int getMunicipio() {
		return Municipio;
	}
	public void setMunicipio(int municipio) {
		Municipio = municipio;
	}
	public double getLongit() {
		return Longit;
	}
	public void setLongit(double longit) {
		Longit = longit;
	}
	public double getLatit() {
		return Latit;
	}
	public void setLatit(double latit) {
		Latit = latit;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}

}
