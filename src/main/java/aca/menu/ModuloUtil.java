// Clase para la tabla de Modulo
package aca.menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ModuloUtil{
		
	public ArrayList<Modulo> getListAll(Connection Conn, String orden ) throws SQLException{
		
		ArrayList<Modulo> lisModulo 	= new ArrayList<Modulo>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID FROM ENOC.MODULO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Modulo modulo = new Modulo();
				modulo.mapeaReg(rs);
				lisModulo.add(modulo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModulo;
	}
		
	public ArrayList<Modulo> getListUser(Connection Conn, String  codigoPersonal, String opciones ) throws SQLException{
		
		ArrayList<Modulo> lisModulo 	= new ArrayList<Modulo>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO,MENU_ID FROM ENOC.MODULO"
					+ " WHERE MODULO_ID IN"
					+ "		(SELECT MODULO_ID FROM ENOC.MODULO_OPCION"
					+ "		WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%'"
					+ "		OR OPCION_ID IN ("+opciones+") ) ORDER BY MENU_ID, 2";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Modulo modulo = new Modulo();
				modulo.mapeaReg(rs);
				lisModulo.add(modulo);
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|getListUser|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModulo;
	}
	
	public boolean getEsMaestro(Connection Conn, String  codigoPersonal) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|getEsMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Valida si el empleado edita contenidos de materias
	public boolean esEditorDeContenidos(Connection Conn, String  codigoPersonal) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT CURSO_ID FROM ENOC.MAPA_NUEVO_CURSO WHERE CODIGO_MAESTRO LIKE '%"+codigoPersonal+"%'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|esEditorDeContenidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean getEsEmpleado(Connection Conn, String  codigoPersonal) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT CLAVE FROM ARON.EMPLEADO WHERE CLAVE = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|getEsEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean getEsMentor(Connection Conn, String  codigoPersonal) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT MENTOR_ID FROM ENOC.MEN_CARRERA "+
			"WHERE MENTOR_ID = '"+codigoPersonal+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|getEsMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public 	HashMap<String, String> getMapAll(Connection Conn) throws SQLException{
		
		HashMap<String, String> lisMenu			= new HashMap<String, String>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT MODULO_ID, (SELECT MENU_NOMBRE FROM ENOC.MENU WHERE MENU_ID = A.MENU_ID) AS MENU_ID FROM ENOC.MODULO A";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisMenu.put(rs.getString("MODULO_ID"), rs.getString("MENU_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.MenuLista|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisMenu;
	}
	
	public HashMap<String, String> mapMenuModulos(Connection conn) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MENU_ID, COUNT(*) AS MODULOS FROM ENOC.MODULO GROUP BY MENU_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("MENU_ID"), rs.getString("MODULOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.ModuloUtil|mapMenuModulos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

	
}