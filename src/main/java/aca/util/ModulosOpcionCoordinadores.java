package aca.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModulosOpcionCoordinadores {	
	
	public static void main(String[]args)throws SQLException{
		Connection 	conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1","enoc","caminacondios");
		
		String moduloId = "A36";
		String opcionId = "710";
		String usuariosActuales = "";

		String query;
		PreparedStatement ps = null;
		ResultSet rs = null;
		query="SELECT USUARIOS FROM ENOC.MODULO_OPCION WHERE MODULO_ID='"+moduloId+"' AND OPCION_ID='"+opcionId+"'"; 
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				usuariosActuales = rs.getString("USUARIOS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.ModulosCoordinadores|main|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		
		String coordinadores = "";
		
		query="SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_CARRERA"; 
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(!usuariosActuales.contains(rs.getString("CODIGO_PERSONAL"))){
					coordinadores+=rs.getString("CODIGO_PERSONAL")+"-";					
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.ModulosCoordinadores|main|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION" +
					" SET USUARIOS = '"+usuariosActuales+coordinadores+"'" +
					" WHERE MODULO_ID='"+moduloId+"' AND OPCION_ID='"+opcionId+"'");
			ps.executeUpdate();
		}catch(Exception ex){
			System.out.println("Error - aca.util.ModulosCoordinadores|main|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		conn.close();
		
		
		System.out.println("Listo");
	}
}