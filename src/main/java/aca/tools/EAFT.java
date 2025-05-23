package aca.tools;

import java.util.ArrayList;

public class EAFT {
	 
	public static void printMatriz(int matriz[][]) throws Exception{
		int valor = 0;
		try{
			// Recorrer matriz
			for (int i = 0; i < matriz.length; i++){
				for (int j=0; j < matriz[i].length; j++){
					valor = matriz[i][j];
					System.out.print(" "+valor);
				}
				System.out.println(" ");
			}
			
		}catch(Exception ex){
			System.out.println("Error:aca.tools|"+ex);
		}		
	}
	
	public static void buscaCamino(int matriz[][], String camino, int nodoActual, int nodoObjetivo, ArrayList lis) throws Exception{
		String caminoOk = "";
		try{
			for(int i= 0; i<matriz[nodoActual].length; i++){
				
				// Si es un enlace valido
				if (matriz[nodoActual][i]!=-1 && matriz[nodoActual][i]!=0 && camino.length()<=6){
					
					// Si llegaste al nodo objetivo
					if (i==nodoObjetivo && camino.indexOf(String.valueOf(i+1))==-1){						
						//System.out.println("Camino:"+camino+String.valueOf(i+1));
						lis.add(camino+String.valueOf(i+1));
					}else{
						// Si no llegas al objetivo continuas buscando
						if (camino.indexOf(String.valueOf(i+1))==-1){
							caminoOk = camino+String.valueOf(i+1);
							buscaCamino(matriz,caminoOk,i,nodoObjetivo,lis);
						}	
					}
				}			
			}			
			
		}catch(Exception ex){
			System.out.println("Error - adm.musica.MusiAlumno|getTelefono|:"+ex);
		}		
	}
	
	public static void main(String[] args) {
		int matriz[][]=  {{0,3,4,-1,-1,-1},{-1,0,4,5,-1,-1},{2,3,0,-1,-1,2},{8,9,5,0,1,-1},{7,2,1,-1,0,-1},{5,-1,4,5,4,0}};
		try{			
			int nodo 			= 4;
			ArrayList lista 	= new ArrayList();
			String camino		= "";
			int peso=0, pesoTemp=0, pesoMin=100;
			int posicion = 0;
			String nodo1="",nodo2="";
			
			//printMatriz(matriz);			
			buscaCamino(matriz, String.valueOf(nodo+1), nodo, 1, lista);
			
			// imprime los caminos
			for(int i=0; i<lista.size();i++){
				//System.out.println(i+": "+lista.get(i));
			}
			
			// Determina el camino mas corto
			for(int i=0; i<lista.size();i++){
				camino = (String)lista.get(i);
				pesoTemp=0; peso=0;
				for (int j=0; j<camino.length()-1;j++){
					pesoTemp=0;
					nodo1 = camino.substring(j,j+1);
					nodo2 = camino.substring(j+1,j+2);
					pesoTemp = matriz[Integer.parseInt(nodo1)-1][Integer.parseInt(nodo2)-1];
					peso += pesoTemp;
					//System.out.println("Matriz:"+nodo1+":"+nodo2+"="+peso);
				}
				if (peso<pesoMin){
					posicion = i;
					pesoMin = peso;		
				}
				//System.out.println("lista:"+i+":"+camino+":"+peso);				
			}
			System.out.println("Minimo:"+posicion+":"+lista.get(posicion)+":"+pesoMin);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}