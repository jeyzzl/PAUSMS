package aca.tools;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.StringTokenizer;

import aca.plan.CursoUtil;
import aca.plan.MapaCurso;
import aca.plan.MapaCursoPre;
import aca.plan.PrerrequisitoUtil;

public class LeerDrive {
	
	public static void main(String[] args) {	
		boolean esPregunta = false;
		try{
			
			String linea 	= "";			
			int rowTotal=0, pregunta=0, itemA = 0;
			String strPregunta = "";
			BufferedReader leerFile = new BufferedReader(new FileReader("C://Trabajo//419-36.BAS"));
			while (leerFile.ready()){
				rowTotal++;
				linea = leerFile.readLine();
				if (
					linea.contains(" 1.") || linea.contains(" 2.") || linea.contains(" 3.") || linea.contains(" 4.") || linea.contains(" 5.") || 
					linea.contains(" 6.") || linea.contains(" 7.") || linea.contains(" 7.") || linea.contains(" 7.") || linea.contains(" 7.") || 
					linea.contains(" 7.") || linea.contains(" 7.") || linea.contains(" 7.") || linea.contains(" 7.") || linea.contains(" 7.")) {
					esPregunta = true;
					strPregunta += linea.replaceAll("PRINT", "").replaceAll("\"", "").trim();
					System.out.println(strPregunta);					
				}
			}	
			leerFile.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}