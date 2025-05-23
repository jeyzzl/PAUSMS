// Clase para la tabla de Alum_Academico
package aca.musica;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class MusiAlumnoUtil{
		
	public ArrayList<MusiAlumno> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiAlumno> lisAlumno	= new ArrayList<MusiAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, INSTITUCION_ID, "
					+ " SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, "
					+ " CODIGO_USUARIO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
	}
	
	public ArrayList<MusiAlumno> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<MusiAlumno> lisAlumno	= new ArrayList<MusiAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, INSTITUCION_ID, "
					+ " SEGURO, TUTOR, TELEFONO, CELULAR, EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, "
					+ " CODIGO_USUARIO, CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE NOMBRE LIKE UPPER('"+nombre+"%') "
					+ " AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%') "
					+ " AND APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MusiAlumno alumno = new MusiAlumno();			
				alumno.mapeaReg(rs);			
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumno;
		
	}
	
	public ArrayList<MusiAlumno> getListInscrito(Connection conn, String periodoId, String orden ) throws SQLException{
		ArrayList<MusiAlumno> lisInscrito 	= new ArrayList<MusiAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "
					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO,"
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID IN "
					+ " (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = '"+periodoId+"' "
					+ " AND ESTADO = 'I') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				lisInscrito.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInscrito;
	}
	
	public ArrayList<MusiAlumno> getListPendientes(Connection conn, String periodoId, String orden ) throws SQLException{//
		ArrayList<MusiAlumno> lisPendientes	= new ArrayList<MusiAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "
					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO, "
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID IN "
					+ " (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = '"+periodoId+"' "
					+ " AND ESTADO = 'C') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				lisPendientes.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getListPendientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPendientes;
	}
	
	public ArrayList<MusiAlumno> getListFormaPago(Connection conn, String periodoId, String formaPago,String orden ) throws SQLException{
		ArrayList<MusiAlumno> lisFormaPago 	= new ArrayList<MusiAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "
					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO, "
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID IN "
					+ " (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = '"+periodoId+"' "
					+ " AND ESTADO = 'I' AND FORMA_PAGO = 'C') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				lisFormaPago.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFormaPago;
	}
	
	public ArrayList<MusiAlumno> getListFormaPagoPag(Connection conn, String periodoId, String formaPagoPag,String sobresueldo,String orden ) throws SQLException{
		ArrayList<MusiAlumno> lisFormaPagoPag 	= new ArrayList<MusiAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, "
					+ " INSTITUCION_ID, SEGURO, TUTOR, TELEFONO, CELULAR, "
					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO, "
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO "
					+ " WHERE CODIGO_ID IN "
					+ " (SELECT CODIGO_ID FROM ENOC.MUSI_CALCULO WHERE PERIODO_ID = '"+periodoId+"' "
					+ " AND ESTADO = 'I' AND FORMA_PAGO = '"+formaPagoPag+"' AND SOBRESUELDO = '"+sobresueldo+"') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				lisFormaPagoPag.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFormaPagoPag;
	}
	
	public static TreeMap<String, MusiAlumno> getMapMusiAlum(Connection conn) throws SQLException{
		
		TreeMap<String,MusiAlumno> treeAlum	= new TreeMap<String,MusiAlumno>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT CODIGO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC,"
					+ " INSTITUCION_ID, SEGURO, TUTOR, COALESCE(TELEFONO,'-') AS TELEFONO, "
					+ " COALESCE(CELULAR,'-') AS CELULAR,"
					+ " EMAIL, CODIGO_UM, COMENTARIO, EMPLEADO, RELIGION_ID, CODIGO_USUARIO, "
					+ " CIUDAD_ID, ESTADO_ID, PAIS_ID, NACIONALIDAD, TEL_TRABAJO, ESTADO, GENERO "
					+ " FROM ENOC.MUSI_ALUMNO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiAlumno alumno = new MusiAlumno();
				alumno.mapeaReg(rs);
				treeAlum.put( rs.getString("CODIGO_ID"), alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getMapMusiAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeAlum;
	}
	
	public static TreeMap<String, String> getMapCelular(Connection conn) throws SQLException{
		
		TreeMap<String,String> treeAlum	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT CODIGO_ID, COALESCE(CELULAR,'-') AS TELEFONO FROM ENOC.MUSI_ALUMNO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				treeAlum.put( rs.getString("CODIGO_ID"), rs.getString("TELEFONO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiAlumnoUtil|getMapAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeAlum;
	}
	
}