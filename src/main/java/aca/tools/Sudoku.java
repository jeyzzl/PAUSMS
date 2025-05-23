package aca.tools;
import java.util.Scanner;
/*
0 0 1 1 5
1 0 0 0 8
1 0 0 2 2
1 0 1 2 3
0 1 0 0 3
0 1 0 1 9
0 1 1 1 6
0 1 2 2 2
1 1 0 0 7
1 1 2 1 8
2 1 0 0 9
2 1 1 2 5
2 1 2 1 7
2 1 2 2 4
0 2 0 1 6
0 2 1 1 7
0 2 2 2 3
1 2 0 0 5
1 2 0 1 4
1 2 1 2 7
2 2 0 0 7
2 2 0 2 2
2 2 2 0 8
2 2 2 1 9
2 2 2 2 6
-2
 */
public class Sudoku{
   
    public static void main(String args[]){
       
        int matriz[][] = new int [9][9];
        int valX=0,valY=0,posX=0,posY=0;
        
        for (int i=0; i<9;i++) {
        	for (int j=0; j<9; j++) {
        		matriz[i][j] = 0;
        	}
        }
        
        boolean entrar = true;
        Scanner teclado = new Scanner(System.in);
        System.out.println("");
        String linea = teclado.nextLine();
        while(entrar){
        	if (!linea.equals("-1")){
        		String[] datos = linea.split(" ");
        		valY = Integer.parseInt(datos[0])*3;
        		valX = Integer.parseInt(datos[1])*3;
        		posY = valY + Integer.parseInt(datos[2]);
        		posX = valX + Integer.parseInt(datos[3]);
        		//System.out.println("PosX="+posX+" PosY="+posY+" : "+datos[4]);
        		matriz[posY][posX] =  Integer.parseInt(datos[4]);
        	}
        	linea = teclado.nextLine();
        	if(linea.contains("-2")){
        		entrar = false;
        	}
        }
        
        teclado.close();
        
        for (int i=0; i<9;i++) {
        	for (int j=0; j<9; j++) {
        		System.out.print(matriz[j][i]+" ");
        	}
        	System.out.println("");
        }
       
    }
}
