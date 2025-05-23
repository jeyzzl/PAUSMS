package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MapaUnidad {	
	public static void main(String[] args) {
		
		try{
			String curso 	= "x";
			String unidad 	= "x";
			String temas 	= "x";
			
			int cont 	= 0;
			int error 	= 0;
			
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;
			PreparedStatement ps2 =null;
			ResultSet rs = null;
			
			ps = Conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, COALESCE(TEMAS, 1) AS TEMAS FROM ENOC.MAPA_NUEVO_UNIDAD"); 
			rs = ps.executeQuery();			
			//ps2 = Conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_UNIDAD SET TMP = ? WHERE CURSO_ID = ? AND UNIDAD_ID = ?"); 
			
			while (rs.next()){ cont++;
				curso 		= rs.getString("CURSO_ID");
				unidad		= rs.getString("UNIDAD_ID");
				if (cont==0){
					System.out.println("Datos1:"+curso+":"+unidad+":");
				}
				temas 		= aca.conecta.Conectar.getStringFromInputStream(rs.getAsciiStream("TEMAS"));
				if (cont==0){
					//System.out.println("Datos:"+curso+":"+unidad);
				}
				/*
				ps2.setString(1, temas);
				ps2.setString(2, curso);
				ps2.setString(3, unidad);
				if (ps2.executeUpdate()==1){
					System.out.println("Row:"+cont+":"+curso+":"+unidad);
					Conn.commit();
				}else{
					error++;
				}
				*/				
			}
			System.out.println("Cont:"+cont+"- Errores:"+error);
			if (Conn!=null) Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}
}