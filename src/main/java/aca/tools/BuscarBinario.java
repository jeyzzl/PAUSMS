package aca.tools;
import java.util.*;
public class BuscarBinario {	
	public static void main(String args[]){
		// Inicializar el arreglo
		int [] arreglo = new int[50];
		for (int i=0; i<arreglo.length; i++) {
			arreglo[i] = i;
		}
		
		// Variables
		int numBuscar 	= 0;
		int posicion	= 0;
		int posIni 		= 0; 
		int posFin 		= arreglo.length;
		
		// Número que vamos a buscar
		Scanner teclado = new Scanner(System.in);
		System.out.println("Ingresa un numero entre 0-50");
		numBuscar 	= teclado.nextInt();
		
		int row		= 0;
		boolean encontre = false;		
		while (encontre == false) {
			row++;
			posicion = ((posFin-posIni) / 2)+posIni;
			System.out.println(row+". Buscando posición:"+posicion+" Inicio:"+posIni+" Fin:"+posFin);
			if (numBuscar==arreglo[posicion]) encontre = true;
			if (numBuscar>arreglo[posicion]){
				posIni 	= posicion;				  
			}else {
				posFin 	= posicion;
			}			
		}
		
		if (encontre) System.out.println("Encontre el numero en la posición:"+posicion);
		System.out.println("Número de Busquedas:"+row);
		
		teclado.close();
    }	
}