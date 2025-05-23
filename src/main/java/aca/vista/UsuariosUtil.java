package aca.vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class UsuariosUtil {

	public ArrayList<Usuarios> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Usuarios> lisUsuario	    	= new ArrayList<Usuarios>();
		Statement st 		                	= conn.createStatement();
		ResultSet rs 				            = null;
		String comando                     		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
					"USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				lisUsuario.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuario;
	}
	
	public ArrayList<Usuarios> getListPorPrefijo(Connection conn, String prefijo, String orden ) throws SQLException{
		
		ArrayList<Usuarios> lisUsuario	    	= new ArrayList<Usuarios>();
		Statement st 		                	= conn.createStatement();
		ResultSet rs 				            = null;
		String comando                     		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO,"
					+ " USUARIO, CLAVE, ESTADO"
					+ " FROM ENOC.USUARIOS"
					+ " WHERE CODIGO_PERSONAL LIKE '"+prefijo+"%' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				lisUsuario.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuario;
	}
	
	public TreeMap<String,Usuarios> getTreeAll(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,Usuarios> treeUsuario	= new TreeMap<String, Usuarios>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				llave = usuario.getCodigoPersonal();
				treeUsuario.put(llave, usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestro|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeUsuario;
	}
	
	public static String getNombreUsuario(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "x";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuariosUtil|getNombreUsuario|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
	
	/*
	 * Nombre corto de los usuarios
	 * */
	public static String getNombreUsuarioCorto(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "X"; 		
 		try{
 			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ? "); 				
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuariosUtil|getNombreUsuarioCorto|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
	
	public static String getNombreAPaternoUsuario(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "x";
 		
 		try{
 			
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 						
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuariosUtil|getNombreUsuario|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
	
public static String getNombreAMaternoUsuario(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "x";
 		
 		try{
 			
 				ps = conn.prepareStatement("SELECT APELLIDO_MATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 						
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuariosUtil|getNombreUsuario|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
	
	public ArrayList<Usuarios> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Usuarios> lisUsuarios	= new ArrayList<Usuarios>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+
				"WHERE NOMBRE LIKE UPPER('%"+nombre+"%') "+
				"AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%') "+
				"AND ( APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') OR APELLIDO_MATERNO IS NULL) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				lisUsuarios.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuarios;
	}
	
	/*
	 * FILTRA A LOS ALUMNOS Y EMPLEADOS EN LA BUSQUEDA.  Valores de opcion = 'Alumno','Empleado'.
	 * */
	public ArrayList<Usuarios> getLista(Connection conn, String nombre, String paterno, String materno, String opcion, String orden ) throws SQLException{
		
		ArrayList<Usuarios> lisUsuarios	= new ArrayList<Usuarios>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String filtro 		= "";
		
		try{
			
			if (opcion.equals("Alumno"))
				filtro = " AND SUBSTR(CODIGO_PERSONAL,1,1) IN ('0','1','2') ";
			else
				filtro = " AND SUBSTR(CODIGO_PERSONAL,1,1) = ('9') ";
			
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS"+
				" WHERE NOMBRE LIKE UPPER('%"+nombre+"%')"+
				" AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%')"+
				" AND ( APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') OR APELLIDO_MATERNO IS NULL)"+
				filtro+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				lisUsuarios.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuarios;
	}
	
	public ArrayList<Usuarios> getListAllFltro(Connection conn, String parametroBuscado, String opcion, String orden ) throws SQLException{
		
		ArrayList<Usuarios> lisUsuario	    	= new ArrayList<Usuarios>();
		Statement st 		                	= conn.createStatement();
		ResultSet rs 				            = null;
		String comando                     		= "";
		String filtro 							= "";
		
		try{
			if (opcion.equals("Alumno"))
				filtro += " AND SUBSTR(CODIGO_PERSONAL,1,1) IN ('0','1','2') ";
			else
				filtro += " AND SUBSTR(CODIGO_PERSONAL,1,1) = ('9') ";
			
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
					"USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS " +
					"WHERE (UPPER(NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO) LIKE '%'||UPPER('"+parametroBuscado+"')||'%') " +
					 filtro+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Usuarios usuario = new Usuarios();
				usuario.mapeaReg(rs);
				lisUsuario.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuario;
	}
	
	
	public static HashMap<String,String> getMapAlumnosNombre(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO " +
				" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ROT_PROGRAMACION) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("CODIGO_PERSONAL");
				map.put(llave, rs.getString("NOMBRE")+" "+rs.getString("APELLIDO_PATERNO")+" "+rs.getString("APELLIDO_MATERNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|getMapAlumnosNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> mapAlumnos(Connection conn ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO FROM ENOC.USUARIOS";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("CODIGO_PERSONAL");
				map.put(llave, rs.getString("NOMBRE")+" "+rs.getString("APELLIDO_PATERNO")+" "+rs.getString("APELLIDO_MATERNO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.UsuariosUtil|mapAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static String getGenero( Connection conn, String codigoPersonal ) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String codigoSe			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT GENERO FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ? "); 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				codigoSe = rs.getString("GENERO");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuarioUtil|getGenero|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return codigoSe;
 	}
}