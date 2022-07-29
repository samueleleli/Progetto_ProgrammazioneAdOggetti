package com.esame.project.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.esame.project.filters.FilterUtils;
import com.esame.project.filters.IFilter;
import com.esame.project.models.StrutturaAlberghiera;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Classe che crea la lista dal dataset.
 */
public class ListaStruttureGenerator implements IFilter<StrutturaAlberghiera, Object> 
{ 
	private FilterUtils<StrutturaAlberghiera> utils;
	private static final String COMMA_DELIMITER = ";";
	@SuppressWarnings({"rawtypes", "unchecked"})
	ArrayList<StrutturaAlberghiera> lista =new ArrayList<StrutturaAlberghiera>();
   /**
    * 
    * @throws FileNotFoundException
    * @throws IOException
    */
	public ListaStruttureGenerator() throws FileNotFoundException, IOException
	{
		super();
		BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));
		String line;
		line = br.readLine();   //salvo la prima riga dei titoli in modo che non crei collisioni dopo
		while ((line = br.readLine()) != null) //legge ogni riga fino a quando non è arrivato alla fine del file 
		{ 
			String[] valori = line.split(COMMA_DELIMITER,14); //salva i valori del dataset in un array di stringhe
			//Controllo Stringhe 
			if(valori[9].equals("Case per ferie")) valori[9]="Case Ferie";
			else if(valori[9].equals("case per ferie")) valori[9]="Case Ferie"; 
			else if(valori[9].equals("Casa per ferie")) valori[9]="Case Ferie"; 
			else if(valori[9].equals("Foresteria Lombarda/Locanda")) valori[9]="Foresteria";
			else if(valori[9].equals("Ostello per la Gioventù")) valori[9]="Ostello";
			else if(valori[9].equals("bed e breakfast")) valori[9]="Bed e Breakfast"; 
			//fine Controllo Stringhe

			//controlli sui valori double e int in modo che non contengano il valore null [5,6,10,11,12]
			if(valori[5].isEmpty()) valori[5]="0";
			if(valori[6].isEmpty()) valori[6]="0";
			if(valori[10].isEmpty()) valori[10]="0";
			if(valori[11].isEmpty()) valori[11]="0";
			if(valori[12].isEmpty()) valori[12]="0";
			// fine controllo sugli int e double
			this.lista.add(new StrutturaAlberghiera (valori[0],valori[1],valori[2],valori[3],valori[4],Integer.parseInt(valori[5]),Integer.parseInt(valori[6]),valori[7],valori[8],valori[9],Integer.parseInt(valori[10]),Double.parseDouble(valori[11]),Double.parseDouble(valori[12]),valori[13]));
			//aggiunge i valori letti a una lista
		}
		br.close();
		this.utils = new FilterUtils<StrutturaAlberghiera>();

	}
	public ArrayList<StrutturaAlberghiera> getLista() {
		return lista;
	}
	void setLista(ArrayList<StrutturaAlberghiera> lista) {
		this.lista = lista;
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
				return (ArrayList<StrutturaAlberghiera>) utils.select(this.getLista(), fieldName, operator, value);
			} catch (NoSuchMethodException e) {
				return new ArrayList<StrutturaAlberghiera>();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				return new ArrayList<StrutturaAlberghiera>();
			}
	}

}

