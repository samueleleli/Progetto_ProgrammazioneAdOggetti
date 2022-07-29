package com.esame.project.models;

import java.util.ArrayList;
import java.util.Collection;

import com.esame.project.filters.IFilter;

/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che contiene la struttura del dataset.
 */
public class StrutturaAlberghiera{
	private String codice;
	private String ubicazione;
	private String areaCompetenza;
	private String via;
	private String civico;
	private int codiceVia;
	private int camere;
	private String camerePiano;
	private String insegna;
	private String tipoAttivita;
	private int municipio;
	private double longit;
	private double latit;
	private String location;
    //classe che contiene i parametri del dataset
	public StrutturaAlberghiera(String codice, String ubicazione, String areaCompetenza, String via, String civico, int codiceVia,
			int camere, String camerePiano, String insegna, String tipoAttivita, int municipio, double longit,
			double latit, String location)  {
		super();
		this.codice = codice;
		this.ubicazione = ubicazione;
		this.areaCompetenza = areaCompetenza;
		this.via = via;
		this.civico = civico;
		this.codiceVia = codiceVia;
		this.camere = camere;
		this.camerePiano = camerePiano;
		this.insegna = insegna;
		this.tipoAttivita = tipoAttivita;
		this.municipio = municipio;
		this.longit = longit;
		this.latit = latit;
		this.location = location;
	}

	public String getCodice() {
		return this.codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getUbicazione() {
		return this.ubicazione;
	}
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}
	public String getAreaCompetenza() {
		return this.areaCompetenza;
	}
	public void setAreaCompetenza(String areaCompetenza) {
		this.areaCompetenza = areaCompetenza;
	}
	public String getVia() {
		return this.via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCivico() {
		return this.civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public int getCodiceVia() {
		return this.codiceVia;
	}
	public void setCodiceVia(int codiceVia) {
		this.codiceVia = codiceVia;
	}
	public int getCamere() {
		return this.camere;
	}
	public void setCamere(int camere) {
		this.camere = camere;
	}
	public String getCamerePiano() {
		return this.camerePiano;
	}
	public void setCamerePiano(String camerePiano) {
		this.camerePiano = camerePiano;
	}
	public String getInsegna() {
		return this.insegna;
	}
	public void setInsegna(String insegna) {
		this.insegna = insegna;
	}
	public String getTipoAttivita() {
		return this.tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public int getMunicipio() {
		return this.municipio;
	}
	public void setMunicipio(int municipio) {
		this.municipio = municipio;
	}
	public double getLongit() {
		return this.longit;
	}
	public void setLongit(double longit) {
		this.longit = longit;
	}
	public double getLatit() {
		return this.latit;
	}
	public void setLatit(double latit) {
		this.latit = latit;
	}
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
