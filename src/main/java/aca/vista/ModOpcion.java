//Clase para la vista MOD_OPCION
package aca.vista;

import java.sql.*;
import java.util.StringTokenizer;

public class ModOpcion{
	private String moduloId;
	private String opcionId;
	private String nombreModulo;
	private String nombreOpcion;
	private String urlModulo;
	private String urlOpcion;
	private String usuarios;

	public ModOpcion(){
		moduloId			="";	
		opcionId			="";
		nombreModulo		="";
		nombreOpcion		="";
		urlModulo			="";
		urlOpcion			="";
		usuarios			="";
	}
	
	public String getModuloId(){
		return moduloId;
	}
	
	public String getOpcionId(){
		return opcionId;
	}
	
	public String getNombreModulo(){
		return nombreModulo;
	}
	
	public String getNombreOpcion(){
		return nombreOpcion;
	}
	
	public String getUrlModulo(){
		return urlModulo;
	}
	
	public String getUrlOpcion(){
		return urlOpcion;
	}
	
	public String getUsuarios (){
		return usuarios;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		moduloId				= rs.getString("MODULO_ID");
		opcionId				= rs.getString("OPCION_ID");
		nombreModulo			= rs.getString("NOMBRE_MODULO");
		nombreOpcion			= rs.getString("NOMBRE_OPCION");
		urlModulo				= rs.getString("URL_MODULO");
		urlOpcion				= rs.getString("URL_OPCION");
		usuarios				= rs.getString("USUARIOS");
	}

	public void mapeaRegId( Connection conn, String moduloId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT MODULO_ID, OPCION_ID,"+
				"NOMBRE_MODULO, NOMBRE_OPCION, URL_MODULO, URL_OPCION, USUARIOS FROM ENOC.MOD_OPCION "+ 
				"WHERE MODULO_ID = ?");
			ps.setString(1, moduloId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MoOpcion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean  ok			= false;
		ResultSet  rs			= null;
		PreparedStatement ps    = null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MOD_OPCION "+ 
				"WHERE MODULO_ID = ? ");
			ps.setString (1, moduloId);
			
			rs = ps.executeQuery();
			if(rs.next())
				ok = true;
			else
				ok = false;
			
		}catch (Exception ex){
			System.out.println("Error - aca.vista.ModOpcion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}

		return ok;
	}
	
	public int numUsuarios(Connection conn) throws SQLException{
		
		ResultSet  rs			= null;
		PreparedStatement ps    = null;
		String usuarios			= "";
		int nUsuarios			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT USUARIOS FROM ENOC.MOD_OPCION "+ 
				"WHERE OPCION_ID = ? ");
			ps.setString (1, opcionId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				usuarios = rs.getString("USUARIOS");
				StringTokenizer token = new StringTokenizer(usuarios,"-");
				nUsuarios = token.countTokens();
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.vista.ModOpcion|numUsuarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}

		return nUsuarios;
	}
	
	public static int numUsuarios(Connection conn, String opcionId) throws SQLException{
		
		ResultSet  rs			= null;
		PreparedStatement ps    = null;
		String usuarios			= "";
		int nUsuarios			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT USUARIOS FROM ENOC.MOD_OPCION "+ 
				"WHERE OPCION_ID = ? ");
			ps.setString (1, opcionId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				usuarios = rs.getString("USUARIOS");
				StringTokenizer token = new StringTokenizer(usuarios,"-");
				nUsuarios = token.countTokens();
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.vista.ModOpcion|numUsuarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}

		return nUsuarios;
	}
}