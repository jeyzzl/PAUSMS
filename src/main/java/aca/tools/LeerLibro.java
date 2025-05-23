package aca.tools;
import java.io.*;
import java.sql.*;


public class LeerLibro {
	
	public static void main(String[] args) {
		
		try{
			
			Connection Conn = null;
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://172.16.7.77:5432/biblia","postgres","jete17");			
						
			String linea 		= "";
			String llave		= "";		
			String libro 		= "";
			String capitulo 	= "";
			String versiculo 	= "";
			int pos 			= 0;			
			
			int rowTotal=0, rowInsert=0, rowError = 0;
			
			BufferedReader leerFile = new BufferedReader(new FileReader("D:\\rv60.txt"));
			while (leerFile.ready()){
				rowTotal++;
				
				linea 		= leerFile.readLine();
				if (linea.length()>5){
					
					pos 		= linea.indexOf(" ");				
					llave 		= linea.substring(0, pos);				
					linea 		= linea.substring(pos+1,linea.length());
					
					llave 		= llave.replace(".", "&&");				
					String[] arreglo = llave.split("&&");
					libro 		= arreglo[0];
					capitulo	= arreglo[1];
					versiculo	= arreglo[2];											
					
					
					PreparedStatement ps = Conn.prepareStatement("INSERT INTO " +
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
					}					
					
				}
				System.out.println(libro+":"+capitulo+":"+versiculo);
				
			}		
			System.out.println("Operaciones: "+ rowTotal+" "+rowInsert+" "+rowError);
			Conn.commit();
			Conn.close();
			System.out.println("Fin...");
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}