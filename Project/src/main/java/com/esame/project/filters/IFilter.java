package com.esame.project.filters;
/**
 * @author Samuele Leli (s1084424@studenti.univpm.it)
 * @version 1.0
 */
/**
 * Interfaccia tra filtri e lista.
 */
import java.util.Collection;

public interface IFilter<E,T> {
	/**
	 * 
	 * @param fieldName parametro che si vuole confrontare
	 * @param operator operatore scelto
	 * @param value parametro che si vuole confrontare
	 * @return la lista
	 */
	abstract Collection<E> filterField(String fieldName, String operator, T value); //Richiama FilterField in ListGenerator 
}
