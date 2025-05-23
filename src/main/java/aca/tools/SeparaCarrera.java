package aca.tools;

import java.util.ArrayList;

public class SeparaCarrera {
	
	public static void main(String[] args) {
		
		try{
			//String s="Licenciatura en Artes Visuales xrea de Cerxmica";
			//String s = "Maestrxa en Administracixn con Acentuacixn en Recursos Humanos";
			//String s = "Ingenierxa en Sistemas Computacionales";
			String s = "";
			int ancho=28;			
		    ArrayList<String> lineas = new ArrayList<String>();			
		   	char car;
		   	int fin=0;
		   	if (s.length()>ancho){				
				fin = ancho;				
		   		while (s.length()>0 && fin>0){
					
		    		car = s.charAt(fin);										
		    		if (car == ' '){
		    			lineas.add(s.substring(0,fin));						
						s = s.substring(fin+1,s.length());						
						if (s.length()>ancho) 
							fin = ancho; 
						else{ 
							fin = s.length();
							lineas.add(s);
							fin=0;
						}						
		    		}else{			
						fin--;
		    		}
		   		}				
		   	}else{ 
		    		lineas.add(s);
		   	}			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}