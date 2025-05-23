package aca.admision;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import adm.alumno.AdmSolicitud;

public class AdmSolicitudUtil {
	public ArrayList<AdmSolicitud> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<AdmSolicitud> lisSolicitud = new ArrayList<AdmSolicitud>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT FOLIO, NOMBRE, APELLIDO_PATERNO," +
					" APELLIDO_MATERNO, CIUDAD_ID, ESTADO_ID," +
					" PAIS_ID, NACIONALIDAD, TO_CHAR(FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC," +
					" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, USUARIO," +
					" CLAVE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, COALESCE(ESTADO,'0') AS ESTADO," +
					" EMAIL, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC"+
					" FROM SALOMON.ADM_SOLICITUD "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				AdmSolicitud sol = new AdmSolicitud();
				sol.mapeaReg(rs);
				lisSolicitud.add(sol);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisSolicitud;
	}
	
	public ArrayList<AdmSolicitud> getListIngreso(Connection conn, String condicion, String orden ) throws SQLException{
		ArrayList<AdmSolicitud> lisSolicitud = new ArrayList<AdmSolicitud>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT FOLIO, NOMBRE, APELLIDO_PATERNO," 
					+ " APELLIDO_MATERNO, CIUDAD_ID, ESTADO_ID,"
					+ " PAIS_ID, NACIONALIDAD, TO_CHAR(FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, USUARIO," 
					+ " CLAVE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, COALESCE(ESTADO,'0') AS ESTADO,"
					+ " EMAIL, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC"
					+ " FROM SALOMON.ADM_SOLICITUD "+ condicion + orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				AdmSolicitud sol = new AdmSolicitud();
				sol.mapeaReg(rs);
				lisSolicitud.add(sol);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisSolicitud;
	}
	
	public ArrayList<ArrayList<String>> getListProceso(Connection conn, String orden) throws SQLException{
		ArrayList<ArrayList<String>> lisSolicitud = new ArrayList<ArrayList<String>>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT FOLIO, NOMBRE, APELLIDO_PATERNO," +
					" APELLIDO_MATERNO, GENERO, COALESCE(ESTADO, '0') AS ESTADO," +
					" EMAIL, MATRICULA, PAIS_ID, " +
					" (SELECT (NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO) AS NOMBRE" +
					"	FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = SALOMON.ADM_SOLICITUD.ASESOR_ID) AS ASESOR_ID, " +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ASESOR_ID AS ASESOR_MAT"+
					" FROM SALOMON.ADM_SOLICITUD "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("FOLIO"));
				arr.add(rs.getString("NOMBRE")+" "+rs.getString("APELLIDO_PATERNO")+" "+(rs.getString("APELLIDO_MATERNO")==null?"":rs.getString("APELLIDO_MATERNO")));
				arr.add(rs.getString("GENERO"));
				arr.add(rs.getString("EMAIL"));
				arr.add(rs.getString("ESTADO"));
				arr.add(rs.getString("ASESOR_ID"));
				arr.add(rs.getString("MATRICULA"));
				arr.add(rs.getString("PAIS_ID"));
				arr.add(rs.getString("FECHA"));
				arr.add(rs.getString("ASESOR_MAT"));
				lisSolicitud.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|getListProceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisSolicitud;
	}
	
	public ArrayList<String> getListaFechas(Connection conn, String orden) throws SQLException{
		ArrayList<String> lisFechas = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(FECHA_INGRESO)AS FECHA FROM SALOMON.ADM_SOLICITUD WHERE ESTADO>=4 AND" +
					" (FECHA_INGRESO LIKE '%'||(TO_CHAR(now(), 'YYYY'))||'%') OR " +
					" (FECHA_INGRESO LIKE '%'||(TO_CHAR(now(), 'YYYY')+1)||'%') "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisFechas.add(rs.getString("FECHA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|getListaFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFechas;
	}
	
	public HashMap<String, String> getAlumnosCreados(Connection conn, String fechaInicio, String fechaFinal, String orden) throws SQLException{		
		
		HashMap<String, String> list		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT MATRICULA, ESTADO_ID "+
					" FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL " +
					" WHERE F_CREADO >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND F_CREADO <= TO_DATE('"+fechaFinal+"','DD/MM/YYYY')) " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				list.put(rs.getString("MATRICULA"), rs.getString("ESTADO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|getAlumnosCreados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<AdmSolicitud> lisAlumnosEnProceso(Connection conn, String year, String condicion, String orden) throws SQLException{
		
		ArrayList<AdmSolicitud> list	= new ArrayList<AdmSolicitud>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," 
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, FECHA_NAC,"
					+ " ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, CLAVE, FECHA,"
					+ " MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, TELEFONO"
					+ " FROM SALOMON.ADM_SOLICITUD "+  condicion +" "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmSolicitud obj = new AdmSolicitud();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|lisAlumnosEnProceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String,String> mapFechaAceptado(Connection conn, String year ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";		
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(F_ADMISION,'DD/MM/YYYY') AS F_ADMISION FROM SALOMON.ADM_PROCESO"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("FOLIO"), rs.getString("F_ADMISION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|mapFechaAceptado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapFechaDocumentos(Connection conn, String year ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";		
		
		try{
			comando = " SELECT FOLIO, TO_CHAR(F_DOCUMENTOS,'DD/MM/YYYY') AS F_DOCUMENTOS FROM SALOMON.ADM_PROCESO"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY')= '"+year+"')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("FOLIO"), rs.getString("F_DOCUMENTOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|mapFechaDocumentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapAlumnosTelefono(Connection conn, String year ) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	            = "";		
		
		try{
			comando = " SELECT CODIGO_PERSONAL, TELEFONO "
					+ " FROM ENOC.ALUM_PERSONAL "
					+ "	WHERE CODIGO_PERSONAL IN( "
					+ "		SELECT MATRICULA FROM SALOMON.ADM_SOLICITUD"
					+ "		WHERE FOLIO IN("
					+ "			SELECT FOLIO FROM SALOMON.ADM_PROCESO "
					+ "			WHERE F_ADMISION IS NOT NULL "
					+ "			AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TELEFONO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmSolicitudUtil|mapAlumnosTelefono|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}