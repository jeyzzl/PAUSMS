package aca.tools;

import java.util.Scanner;

public class MiClase {
	
	public static void main(String[] args){
		
		int arreglo[][] = new int[5000][5000];
		int nuevaX = 0; int nuevaY = 0;
		int viejaX = 0; int viejaY = 0;
		
		for (int i=0; i<5000; i++){
			for (int j=0; j<5000; j++) {
				arreglo[i][j] = -1;
 			}			
		}
		
		Scanner teclado = new Scanner(System.in);
	    System.out.println("Número de elementos:");
	    int elementos = teclado.nextInt();
	    
		for (int row=1; row <= elementos; row++){
			if (row % 2 == 1) {
				nuevaX = row - viejaY;
				nuevaY = viejaY + 1;
			}else{
				nuevaY = viejaX - 1;
				nuevaX = row - nuevaY;
			}
			arreglo[nuevaX][nuevaY] = row;
			viejaX = nuevaX;
			viejaY = nuevaY;
			//System.out.println("("+nuevaX+","+nuevaY+")="+row);			
		}
		
		int posX = 0;
		int posY = 0;
		boolean error = false;
		while (error == false) {
			System.out.println("Valor de X:");
		    posX = teclado.nextInt();
		    System.out.println("Valor de Y:");
		    posY = teclado.nextInt();
		    if (arreglo[posX][posY] == -1) {
		    	error = true;
		    	System.out.println("Vacío");
		    }else {
		    	System.out.println("Encontre:"+arreglo[posX][posY]);		    	
		    }
		    System.out.println(" ");
		}		
	}
}
