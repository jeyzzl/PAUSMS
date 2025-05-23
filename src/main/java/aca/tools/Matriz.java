package aca.tools;

import java.util.Scanner;
/**
 * Write a description of class Arreglo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Matriz{
   
    public static void main( String args[]){
       
        Scanner teclado = new Scanner(System.in);
        System.out.print('\u000C');
        System.out.println("Captura la dimension");
        int dimension = teclado.nextInt();
       
        String clave = "luna";
        String codigo = "";
       
        //Variables para el recorrido
        int fila        = 0;
        int columna     = 0;
        int posFila     = 0;
        int posColumna  = 0;
       
        for(int c=0; c<clave.length(); c++){
            int letra = clave.charAt(c);
            codigo += Integer.toBinaryString(letra);
        }
            System.out.println(codigo);
       
        int matriz[][] = new int [dimension][dimension];
       
        for(int f=0; f<dimension; f++){
            for(int c=0; c<dimension; c++){
                matriz [f][c] = 0;
            }
        }
        boolean fin = false;
        int num = 0;
       
        while(num<codigo.length() && fin==false){
        	String valor = String.valueOf(codigo.charAt(num));
        	//System.out.println("Coordenadas:"+fila+","+columna+":"+valor);            
            matriz[fila][columna] = Integer.parseInt(valor);
            if(columna == 0){
                if(posColumna + 1 < dimension){
                    posColumna = posColumna + 1;
                }else if(posFila + 1 < dimension){
                    posFila = posFila + 1;
                }
                columna 	= posColumna;
                fila 		= posFila;
            }else if(fila + 1 == dimension){
            	if (fila+1==dimension && columna+1==dimension) fin = true;
            	posFila 	= posFila + 1;
                fila 		= posFila;
                columna 	= posColumna;                 
            }else {	
                fila 	= fila + 1;
                columna = columna - 1;
            }
            num++;
        }
       
        if(fin){
            System.out.println("Se acabaron los espacios");
        }
        for(int f = 0; f<dimension; f++){
            for(int c=0; c<dimension; c++){
                System.out.print(matriz[f][c] + "  ");
            }
            System.out.println("");
        }
    }
}