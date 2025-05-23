package aca.vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class MaestrosUtil {

	public ArrayList<Maestros> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Maestros> lisMaestro	    	= new ArrayList<Maestros>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					"TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Maestros emaestro = new Maestros();
				emaestro.mapeaReg(rs);
				lisMaestro.add(emaestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public ArrayList<Maestros> getListMaestros(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Maestros> lisMaestro	    	= new ArrayList<Maestros>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS" +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Maestros emaestro = new Maestros();
				emaestro.mapeaReg(rs);
				lisMaestro.add(emaestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|getListMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public ArrayList<Maestros> ListaEnCargasyModalidades(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		
		ArrayList<Maestros> lisMaestro	    	= new ArrayList<Maestros>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN"
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Maestros emaestro = new Maestros();
				emaestro.mapeaReg(rs);
				lisMaestro.add(emaestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|ListaEnCargasyModalidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public ArrayList<Maestros> getListMaestros(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Maestros> lisMaestro	    		= new ArrayList<Maestros>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					"TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS " +
					"WHERE UPPER(NOMBRE) LIKE UPPER('"+nombre+"%') "+
					"AND UPPER(APELLIDO_PATERNO) LIKE UPPER('%"+paterno+"%') "+
					"AND UPPER(APELLIDO_MATERNO) LIKE UPPER('%"+materno+"%')" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Maestros emaestro = new Maestros();
				emaestro.mapeaReg(rs);
				lisMaestro.add(emaestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpMaestroUtil|getListMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public static TreeMap<String, Maestros> getTreeAll(Connection conn, String orden) throws SQLException{
		
		TreeMap<String,Maestros> treeEmp	= new TreeMap<String, Maestros>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
				" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Maestros maestro = new Maestros();
				maestro.mapeaReg(rs);
				llave = maestro.getCodigoPersonal();
				treeEmp.put(llave, maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.MaestroUtil|getTreeAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeEmp;
	}
	
	public static String getNombreMaestro(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.MaestrosUtil|getNombreMaestro|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}	
	
	public static String getNombreCorto(Connection conn, String codigoPersonal, String opcion) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			if (opcion.equals("NOMBRE")){
 				ps = conn.prepareStatement("SELECT SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else if (opcion.equals("APELLIDO")){
 				ps = conn.prepareStatement("SELECT APELLIDO_PATERNO||' '||SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END) AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}else{
 				ps = conn.prepareStatement("SELECT SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 					"WHERE CODIGO_PERSONAL = ? ");
 			}			
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("NOMBRE");
 			else
 				nombre = "0000000";
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.MaestrosUtil|getNombreMaestro|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return nombre;
 	}
	
	public static String getGenero(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
 		ResultSet 		rs		= null; 		
 		String genero			= "-";
 		
 		try{ 			
 			ps = conn.prepareStatement("SELECT GENERO FROM MAESTROS WHERE CODIGO_PERSONAL = ?");
 				
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				 genero = rs.getString("GENERO");		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.MaestrosUtil|getGenero|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return genero;
 	}
	
	public static String getEquipo(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
 		ResultSet 		rs		= null; 		
 		String equipo			= "0";
 		
 		try{ 			
 			ps = conn.prepareStatement("SELECT COALESCE(EQUIPO_ID, 0) AS EQUIPO FROM POR_REGISTRO WHERE CODIGO_PERSONAL = ?");
 				
 			ps.setString(1,codigoPersonal); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				 equipo = rs.getString("EQUIPO");		
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.MaestrosUtil|getEquipo|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { } 
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return equipo;
 	}
	
	public ArrayList<Maestros> getListEmp(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Maestros> lisMaestros	= new ArrayList<Maestros>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+
				"FROM ENOC.MAESTROS "+
				"WHERE CODIGO_PERSONAL LIKE '9%' "+
				"AND NOMBRE LIKE UPPER('%"+nombre+"%') "+
				"AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
				"AND (APELLIDO_MATERNO LIKE UPPER('"+materno+"%') OR APELLIDO_MATERNO IS NULL) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Maestros maestro = new Maestros();
				maestro.mapeaReg(rs);
				lisMaestros.add(maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|getListEmp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestros;
	}
	
	public static ArrayList<Maestros> getListMaestroEval(Connection conn, String cargas, String orden) throws SQLException{
		
		ArrayList<Maestros> lisMaestros = new ArrayList<Maestros>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+
				"FROM ENOC.MAESTROS "+				
				"WHERE CODIGO_PERSONAL IN " +
				"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+") ) "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Maestros maestro = new Maestros();
				maestro.mapeaReg(rs);
				lisMaestros.add(maestro);				
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|getListMaestroEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisMaestros;
	}
	
	/* Lista de Maestros evaluados en una determinada evaluacixn docente*/
	public static ArrayList<Maestros> getListMaestroEvalDocente(Connection conn, String edoId, String orden) throws SQLException{
		
		ArrayList<Maestros> lisMaestros = new ArrayList<Maestros>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"+
				" F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"+
				" FROM ENOC.MAESTROS"+				
				" WHERE CODIGO_PERSONAL IN" +
				" (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = "+edoId+")) "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Maestros maestro = new Maestros();
				maestro.mapeaReg(rs);
				lisMaestros.add(maestro);				
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|getListMaestroEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisMaestros;
	}
	
	public static HashMap<String,String> getMaestroEdad(Connection conn ) throws SQLException{
		
		HashMap<String,String> mapaEmp	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";	
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ENOC.EMP_EDAD(CODIGO_PERSONAL) AS EDAD FROM ENOC.MAESTROS"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaEmp.put(rs.getString("CODIGO_PERSONAL"), rs.getString("EDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|getMaestroEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapaEmp;
	}
	
	public static HashMap<String,Maestros> mapaMaestros(Connection conn ) throws SQLException{
		
		HashMap<String,Maestros> mapaEmp	= new HashMap<String, Maestros>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";	
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Maestros maestro = new Maestros();
				maestro.mapeaReg(rs);
				mapaEmp.put(rs.getString("CODIGO_PERSONAL"), maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|mapaMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapaEmp;
	}
	
	public static HashMap<String,String> mapMaestroNombre(Connection conn, String opcion ) throws SQLException{
		
		HashMap<String,String> mapaEmp	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.MAESTROS";
			}else{
				comando = "SELECT CODIGO_PERSONAL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.MAESTROS";
			}
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaEmp.put(rs.getString("CODIGO_PERSONAL"), rs.getString("NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestrosUtil|mapMaestroNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapaEmp;
	}	
	
}