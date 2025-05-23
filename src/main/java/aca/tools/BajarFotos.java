package aca.tools;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class BajarFotos {
	
	public static void main(String[] args) throws SQLException {
		
		Connection conPos		= null;
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		try{	
		
			String dir				= "C:\\Trabajo\\fotos\\";
			
			int rowTotal=0;			
			DriverManager.registerDriver (new org.postgresql.Driver());
        	conPos	= DriverManager.getConnection("jdbc:postgresql://172.16.251.11/archivo","postgres","jete17");
        	
			String query =  "SELECT CODIGO_PERSONAL, TIPO, FOTO FROM ALUM_FOTO WHERE CODIGO_PERSONAL IN (SELECT CODIGO FROM BAJAR_FOTO WHERE FACULTAD = 'POSGRADO')";
			ps= conPos.prepareStatement(query); 
			rs = ps.executeQuery();
			while (rs.next()) {
				rowTotal++;
				System.out.println(rs.getString("CODIGO_PERSONAL"));
				byte[] imagen	= rs.getBytes("FOTO");
				// Escribir el archivo en el directorio del servidor de aplicaciones con el objeto FileOutputStream 
	    		FileOutputStream fos = new FileOutputStream(dir+rs.getString("CODIGO_PERSONAL")+".jpg");	    		
	    		fos.write(imagen);		
	    		fos.flush();	    		
	    		// Cerrar los objetos
	    		if (fos!=null) fos.close();		
			}
		
			System.out.println("Fin -> "+rowTotal);
					
		}catch(Exception e){
			System.out.println(e);
		}finally {
			if (conPos!=null) conPos.close(); 
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}

	}

}