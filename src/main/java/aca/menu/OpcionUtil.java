// Clase para la tabla de MODULO_OPCION
package aca.menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpcionUtil{
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Connection conn, Opcion opcion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MODULO_OPCION"
					+ " (MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, ICONO, ORDEN, USUARIOS, CARPETA)"
					+ " VALUES(?,?,?,?,?,TO_NUMBER(?,'99'),?, ?)");
			ps.setString(1, opcion.getModuloId());
			ps.setString(2, opcion.getOpcionId());
			ps.setString(3, opcion.getNombreOpcion());
			ps.setString(4, opcion.getUrl());
			ps.setString(5, opcion.getIcono());
			ps.setString(6, opcion.getOrden());
			ps.setString(7, opcion.getUsuarios());
			ps.setString(8, opcion.getCarpeta());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, Opcion opcion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION"
					+ " SET NOMBRE_OPCION = ?, URL= ?, ICONO= ?,"
					+ " ORDEN = TO_NUMBER(?,'99'), USUARIOS = ?,"
					+ " CARPETA = ?"
					+ " WHERE MODULO_ID = ? AND OPCION_ID = ?");	
			
			ps.setString(1, opcion.getNombreOpcion());
			ps.setString(2, opcion.getUrl());
			ps.setString(3, opcion.getIcono());
			ps.setString(4, opcion.getOrden());
			ps.setString(5, opcion.getUsuarios());
			ps.setString(6, opcion.getCarpeta());
			ps.setString(7, opcion.getModuloId());
			ps.setString(8, opcion.getOpcionId());		
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateOpcion(Connection conn, String usuarios, String opcionId  ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION "+ 
				"SET USUARIOS = ? "+
				"WHERE  OPCION_ID = ?");
			ps.setString(1,usuarios);
			ps.setString(2,opcionId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|updateOpcion|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean borrarUsuario(Connection conn, String usuario) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION"
					+ " SET USUARIOS = REPLACE(USUARIOS,?,'')"
					+ " WHERE USUARIOS LIKE '%"+usuario+"%'");
			ps.setString(1,usuario);			
			if ( ps.executeUpdate() >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|borrarUsuario|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateUsuarios(Connection conn, String usuarios, String opcion) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION SET USUARIOS = ? WHERE OPCION_ID = ?");
			ps.setString(1, usuarios);
			ps.setString(2, opcion);
			if ( ps.executeUpdate() == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|updateUsuarios|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean limpiarGuiones(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_OPCION"
					+ " SET USUARIOS = REPLACE(USUARIOS,'--','-')"
					+ " WHERE USUARIOS LIKE '%--%'");						
			if ( ps.executeUpdate() >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|limpiarGuiones|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String moduloId, String opcionId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ?"); 
			ps.setString(1,moduloId);
			ps.setString(2,opcionId);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean existeReg(Connection conn, String moduloId, String opcionId) throws SQLException{
		boolean ok 			= false;		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		try{			
			ps = conn.prepareStatement("SELECT * FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ?"); 
			ps.setString(1, moduloId);
			ps.setString(2, opcionId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;		
	}
	
	public static String usuariosOpcion(Connection conn, String opcionId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String rolNombre 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT USUARIOS FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?"); 
			ps.setString(1,opcionId);
			rs = ps.executeQuery();
			if (rs.next()){
				rolNombre = rs.getString("USUARIOS");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|usuariosOpcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rolNombre;
	}
	
	public static String opcionNombre(Connection conn, String opcionId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String rolNombre 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_OPCION, MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?"); 
			ps.setString(1,opcionId);
			rs = ps.executeQuery();
			if (rs.next()){
				rolNombre = rs.getString("NOMBRE_OPCION")+"@@"+rs.getString("MODULO_ID");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|opcionNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rolNombre;
	}
	
	public boolean tienePermiso(String ruta, String usuario){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM MODULO_OPCION WHERE CARPETA = ? AND INSTR(USUARIOS,?) > 0";			
			Object[] parametros = new Object[]{ruta, usuario};
			
			if (enocJdbc.queryForObject(comando,parametros,Integer.class) >= 1){
				ok = true;				
			}
			
		}catch( Exception ex){
			System.out.println("Error:aca.menu.OpcionUtil|opcionNombre|"+ex);
		}
		
		return ok;		
	}	
	
	// Regresa un listor con todos los elementos de la tabla Modulo_opcion 
	public ArrayList<Opcion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Opcion> lisOpcion	= new ArrayList<Opcion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, "+
				"URL, ICONO, ORDEN, USUARIOS, CARPETA "+
				"FROM ENOC.MODULO_OPCION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Opcion opcion = new Opcion();
				opcion.mapeaReg(rs);
				lisOpcion.add(opcion);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisOpcion;
	}
	
	// Regresa un listor del Objeto "Opcion" con las opciones a las que tiene acceso el usuario
	public ArrayList<Opcion> getListUser(Connection conn, String codigoPersonal, String opciones ) throws SQLException{
		
		ArrayList<Opcion> lisOpcion 	= new ArrayList<Opcion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA"
					+ " FROM ENOC.MODULO_OPCION"
					+ " WHERE ICONO = 'A'"
					+ " AND USUARIOS LIKE '%-"+codigoPersonal+"-%'"
					+ " OR OPCION_ID IN ("+opciones+")"
					+ " ORDER BY 1,3";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Opcion opcion = new Opcion();
				opcion.mapeaReg(rs);
				lisOpcion.add(opcion);							
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getListUser|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisOpcion;
	}
	
	// Regresa un String que contiene las opciones a que tiene acceso el usuario.
	public String getOpcUser(Connection conn, String codigoPersonal ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String opcion		= "";
		
		try{
			comando = "SELECT OPCION_ID FROM ENOC.MODULO_OPCION"+ 
					" WHERE ICONO = 'A' AND USUARIOS LIKE '%-"+codigoPersonal+"-%'";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				opcion = opcion +rs.getString("OPCION_ID")+" ";				
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getOpcUser|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return opcion;
	}
	
	
	public HashMap<String, String> mapModuloOpciones(Connection conn) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MODULO_ID, COUNT(*) AS OPCIONES FROM ENOC.MODULO_OPCION GROUP BY MODULO_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("MODULO_ID"), rs.getString("OPCIONES"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|mapModuloOpciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapCarpetas(Connection conn, String codigoPersonal) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARPETA FROM ENOC.MODULO_OPCION WHERE USUARIOS LIKE '%"+codigoPersonal+"%'";
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("CARPETA"), rs.getString("CARPETA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|mapCarpetas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}