package com.bus.preguntas1_2_3;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Clase donde se hace uso de estruturas de datos y algoritmos de filtrado por patron de java 8
 * Puntro 1) What data structures and algorithms can be used for storing and filtering by pattern
 * (or regex) a set of strings? Write the code using Java 8 functions and Stream API.
 */
public class Punto1 {
	
	private List<String> listaOriginal;
	private List<String> listaFiltrada;
	Predicate<String> patronNumerico;
	Predicate<String> patronTexto;
	Predicate<String> patronCorreo;
	
	/**
	 * Metodo constructor en el cual se inicializan los patrones de filtrado.
	 * @param lista Lista que contiene los valores iniciales a filtrar
	 */
	public Punto1(List<String> lista) {
		this.listaOriginal = lista;
		this.patronNumerico = Pattern.compile("[0-9]+").asPredicate();
		this.patronTexto = Pattern.compile("^a").asPredicate();
		this.patronCorreo = Pattern.compile("^(.+)@(.+).(.+)$").asPredicate();
	}

	/**
	 * Metodo encargado de aplicar el filtrado a la lista
	 * @param regex Criterio de filtrado que sera aplciado
	 */
	private void filtrar(Predicate<String> regex) {
		this.listaFiltrada = 	(List<String>) listaOriginal.stream().filter(regex)
								.collect(Collectors.<String>toList());
	}

	/**
	 * Metodo que imprime los datos contenidos en una lista
	 * @param lista
	 */
	private void imprimirLista (List<String> lista) {
		lista.forEach(System.out::println);
	}

	/**
	 * Metodo de interaccion con el usuario imprime por consola la información necesario
	 * y captura las opciones seleccionadas por el usurio
	 */
	private void selectorDeOpciones() {
		//Valor usado como selector de opcion
		int seleccion = 0;
		Scanner scanner = new Scanner(System.in);
		//mensaje inicial de interacción
		System.out.print("Que patron de filtrado desea aplicar a aplicar a la lista: ");

		//Se realiza captura del dato
		try {
			seleccion = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Ingrese un valor valido.");
			selectorDeOpciones();
		}

		//Se ejecuta la accion asociada al caso seleccionado por el usuario
		switch (seleccion) {
		case 1:
			this.filtrar(patronNumerico);
			System.out.println("Lista filtrada por numeros.");
			this.imprimirLista(listaFiltrada);
			System.out.println("");
			selectorDeOpciones();
			break;
		case 2:
			this.filtrar(patronTexto);
			System.out.println("Lista filtrada iniciando con a.");
			this.imprimirLista(listaFiltrada);
			System.out.println("");
			selectorDeOpciones();
			break;
		case 3:
			this.filtrar(patronCorreo);
			System.out.println("Lista filtrada por formato de correo.");
			this.imprimirLista(listaFiltrada);
			System.out.println("");
			selectorDeOpciones();
			break;
		case 4:
			System.out.println("Aplicacion cerrada.");
			break;
		default:
			System.out.println("Ingrese un valor valido.");
			selectorDeOpciones();
		}
	}

	public static void main(String[] args) {
		//Datos iniciales del arreglo a filtrar
		List<String> lista = Arrays.asList("123", "456", "789",
										   "abc", "ade", "agh",
										   "correo@prueba.co", "e@mail.com", "mi@correo.com");
		
		Punto1 punto1 = new Punto1(lista);
		
		System.out.println("Valores iniciales de la lista:");
		punto1.imprimirLista(punto1.listaOriginal);
		System.out.println("");
		System.out.println("Ingrese 1 para filtrado numerico.");
		System.out.println("Ingrese 2 para filtrar los que inician con a.");
		System.out.println("Ingrese 3 para filtrado de formato de correo.");
		System.out.println("Ingrese 4 para salir.\n");
		
		punto1.selectorDeOpciones();
	}

}
