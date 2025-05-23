package aca.tools;

import java.io.*;
import java.util.*;

public class FileToMap {
	
	public static void main(String[] args) {
		
		long timeInicio, timeFinal;
	    timeInicio = System.currentTimeMillis();
	    
		HashMap<String,String> mapIdioma = new HashMap<String,String>();
		
		try{
			String linea 	= "";			
			BufferedReader leerFile = new BufferedReader(new FileReader("C://English.txt"));
			while (leerFile.ready()){
				linea = leerFile.readLine();
				String [] texto = linea.split("==");
				mapIdioma.put(texto[0].trim(), texto[1]);
				//System.out.println("Datos:"+texto[0]+":"+texto[1]);
			}
			leerFile.close();
			if (mapIdioma.containsKey("Alumno1"))
				System.out.println("Alumno1:"+mapIdioma.get("Alumno1"));
			timeFinal = System.currentTimeMillis();
			
			System.out.println("Time:"+(timeFinal-timeInicio));
		}catch(Exception e){
			System.out.println(e);
		}
	}
}