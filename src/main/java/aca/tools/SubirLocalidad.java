package aca.tools;
import java.io.*;
import java.sql.*;
import java.util.*;

import aca.plan.MapaCurso;
import aca.plan.MapaCursoPre;

public class SubirLocalidad {
	
	public static void main(String[] args) {		
		
		try{		
			
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			
			String linea 			= "";
			String estadoId 		= "";
			String estadoNombre		= "";
			String ciudadId			= "";
			String ciudadNombre		= ""; 
			String localidadId		= "";
			String localidadNombre	= "";
			
			
			int rowTotal=0, rowInsert=0, rowError = 0;
			
			BufferedReader leerFile = new BufferedReader(new FileReader("/home/josetorres/Trabajo/Estados/AGUASCALIENTES2.csv"));
			while (leerFile.ready()){
				rowTotal++;
				linea = leerFile.readLine();
				StringTokenizer st = new StringTokenizer(linea,",");
				estadoId 		= st.nextToken();
				estadoNombre	= st.nextToken();
				ciudadId		= st.nextToken();
				ciudadNombre	= st.nextToken();
				localidadId		= st.nextToken();
				localidadNombre	= st.nextToken();			
				System.out.println(rowTotal+":"+estadoNombre+":"+ciudadNombre);
				
				/*
				MapaCurso mp = new MapaCurso();
				mp.setCursoId(plan+curso);
				if (mp.existeReg(Conn)){
					mp.setCursoId(plan+prerre);
					if (mp.existeReg(Conn)){
						MapaCursoPre mcp = new MapaCursoPre();
						mcp.setCursoId(plan+curso);
						mcp.setCursoIdPre(plan+prerre);
						if (mcp.insertReg(Conn)){
							rowInsert++;
						}else{
							rowError++;
						}
					}					
				}
				*/
				
				//System.out.println("Datos: "+plan+curso+" - "+plan+prerre);
			    
			}		
			//System.out.println("Operaciones: "+ rowTotal+" "+rowInsert+" "+rowError);
			Conn.commit();
			Conn.close();
			//System.out.println("Fin");			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}