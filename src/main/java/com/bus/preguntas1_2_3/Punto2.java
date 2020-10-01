package com.bus.preguntas1_2_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase donde se implementa una función de conteo de caracteres en una cadena, retornado el valor de
 * repeticiones de cada caracter en caso de que este sea mayor o igual que 2
 * Puntro 1) Write a function that returns the count of distinct case-insensitive alphabetic characters
 * and numeric digits occurring more than once in an input string.
 */
public class Punto2 {
	
	private String cadenaOriginal;
	private List<String> cadenaLowerCase;
	private List<String> caracter;
	private List<Integer> cantidad;

	/**
	 * Metodo constructor en el cual se toma como entrada la cadena a procesar y se inicizal los
	 * arreglos auxiliares para el proceso
	 * @param cadena Cadena de texto a procesar
	 */
	public Punto2(String cadena) {
		this.cadenaOriginal = cadena;
		//Arreglo que contiene las letras de la cadena original toda en minuscula
		this.cadenaLowerCase = new ArrayList<String>(Arrays.asList(cadena.toLowerCase().split("")));
		//Arreglo que contiene las letras de la cadena en minusculas y sin repetición
		this.caracter = cadenaLowerCase.stream().distinct().collect(Collectors.toList());
		//Arreglo de enteros inicializado en 0 que sera usado como contador de repeticiones
		//de las letras, su tamaño esta dado por la cantidad de letras en minuscula sin repeticion
		this.cantidad = new ArrayList<Integer>(Collections.nCopies(caracter.size(), 0));
	}

	/**
	 * Metodo encargado de realizar el conteo de las repeticiones de cada letra en la cadena
	 * original en minuscula
	 */
	private void procesar() {
		//Se recorre el arreglo de letras en minuscula (Copia del original)
		for (String letra:cadenaLowerCase) {
			//Se obtiene la posición de la letra en el arreglo de caracteres sin repeticion
			int posicion = caracter.indexOf(letra);
			//Se aumenta el contador de cantidad de la letra
			cantidad.set(posicion, cantidad.get(posicion) + 1);
		}
	}

	/**
	 * Metodo encargado de imprimir el resultado en el formato solicitado
	 */
	private void imprimirResultado() {
		//Se imprime la cadena origiral y se inicia con la impresión del conteo
		System.out.print(cadenaOriginal + " => {");
		
		for (int i = 0; i < caracter.size(); i++) {
			//Solo se imprimen las letras cuya repetición es mayor o igual que 2
			if(this.cantidad.get(i) >= 2 ) {
				System.out.print ("'"+this.caracter.get(i) + "': " + this.cantidad.get(i)+" " );
			}
		}
		//Se cierra la linea impresa con el formato solicitado
		System.out.print("}");
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese la cadena a analizar: ");
		String cadena = scanner.next();

		Punto2 punto1 = new Punto2(cadena);

		punto1.procesar();
		punto1.imprimirResultado();
	}
}
