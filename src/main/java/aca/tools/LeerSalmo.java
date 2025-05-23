package aca.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LeerSalmo {
	
	public static void main(String[] args) {		
		
		try{		
			
			/*Connection Conn = null;
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://172.16.7.77:5432/biblia","postgres","jete17");*/			
						
			String linea 		= "";			
			String versiculo 	= "";
			int pos 			= 0;
			int posicion        = 0;
			
			int rowTotal=0, rowInsert=0, rowError = 0;
			
			BufferedReader leerFile = new BufferedReader(new FileReader("/home/academico/Salmo.txt"));
			while (leerFile.ready() && rowTotal<10){
				rowTotal++;
				
				linea 		= leerFile.readLine();
				pos 		= linea.charAt('[');
				posicion	= linea.charAt(']');
				linea 		= linea.substring(pos,posicion);				
				
				
				//System.out.println("Datos:"+linea);
				
				/*PreparedStatement ps = Conn.prepareStatement("INSERT INTO " +
						"BIB_VERSICULO(LIBRO_ID,CAPITULO_ID,VERSICULO_ID,VERSICULO_TEXTO)" +
						"VALUES(TO_NUMBER(?,'99'),TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?)");
				ps.setString(1, libro);
				ps.setString(2, capitulo);
				ps.setString(3, versiculo);
				ps.setString(4, linea);
				if (ps.executeUpdate()==1){
					Conn.commit();
					rowInsert++;
				}else{
					rowError++;
				}*/
			    
				
			}		
			//System.out.println("Operaciones: "+ rowTotal+" "+rowInsert+" "+rowError);
			//Conn.commit();
			//Conn.close();
			//System.out.println("Fin...");
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}