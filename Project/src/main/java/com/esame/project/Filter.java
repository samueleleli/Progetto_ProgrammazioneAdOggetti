package com.esame.project;

import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public interface Filter<E,T> {
	abstract Collection<E> filterField(String fieldName, String operator, T value); //Richiama FilterField in ListGenerator 
}
