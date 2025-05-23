package  aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import aca.edo.EdoUtil;

public class EstadisticaUtil{
	
	public ArrayList<Estadistica> getList(Connection conn, String cargaId) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT "+
					"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+					
					"CARGA_ID, BLOQUE_ID, "+
					"NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
					"TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO, "+
					"CARRERA_ID, "+
					"RELIGION_ID,"+
					"CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID, " +
					"CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO, "+
					"PLAN_ID,"+
					"FACULTAD_ID, "+
					"MODALIDAD_ID, "+
					"PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD "+
					"FROM ENOC.ESTADISTICA "+
					"WHERE CARGA_ID = '"+cargaId+"' "+
					"ORDER BY CARRERA_ID";
			
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						Estadistica estadistica = new Estadistica();
						estadistica.mapeaRegCorto2(rs);
						lisEstadistica.add(estadistica);
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListOrden(Connection conn, String cargaId, String order) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT "+
					"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+					
					"CARGA_ID, BLOQUE_ID, "+
					"NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
					"TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO, "+
					"CARRERA_ID, "+
					"RELIGION_ID,"+
					"CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID, " +					
					"CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO, "+
					"PLAN_ID,"+
					"FACULTAD_ID, "+
					"MODALIDAD_ID, "+
					"PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD "+
					"FROM ENOC.ESTADISTICA "+
					"WHERE CARGA_ID = '"+cargaId+"' "+order;
			
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						Estadistica estadistica = new Estadistica();
						estadistica.mapeaRegCorto2(rs);
						lisEstadistica.add(estadistica);
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListOrdenModalidad(Connection conn, String cargaId, String modalidadId, String order) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID,"
					+ " CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " F_INSCRIPCION,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD"
					+ " FROM ENOC.ESTADISTICA "
					+ " WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidadId+") "+order;
			//System.out.println(comando);
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						Estadistica estadistica = new Estadistica();
						estadistica.mapeaRegCorto2(rs);
						lisEstadistica.add(estadistica);
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListOrdenModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> lisCargaModalidadesyFechas(Connection conn, String cargaId, String modalidadId, String fechaIni, String fechaFin, String order) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID,"
					+ " CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD"
					+ " FROM ENOC.ESTADISTICA "
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidadId+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+order;			
			rs = st.executeQuery(comando);
			while (rs.next()){					
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegCorto2(rs);
				lisEstadistica.add(estadistica);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListOrdenModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> lisCargaModalidadesFechasYResidencia(Connection conn, String cargaId, String modalidadId, String fechaIni, String fechaFin, String residencia, String nacionalidad, String tipoAlumno, String order) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID,"
					+ " CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " F_INSCRIPCION,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD"
					+ " FROM ENOC.ESTADISTICA "
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidadId+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " "+residencia+" "+nacionalidad+" "+tipoAlumno+" "+order;
			rs = st.executeQuery(comando);
			while (rs.next()){					
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegCorto2(rs);
				lisEstadistica.add(estadistica);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListOrdenModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> lisInscritosPorFechas(Connection conn, String fechaIni, String fechaFin, String orden) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando = " SELECT CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+ orden;			
					rs = st.executeQuery(comando);
					while (rs.next()){						
						Estadistica estadistica = new Estadistica();
						estadistica.mapeaReg(rs);
						lisEstadistica.add(estadistica);
					}					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|lisInscritosPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> lisCambioCarreraPorFechas(Connection conn, String fechaIni, String fechaFin, String cargas, String tipos, String orden) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		/* 'I','1','2','4','5','6' */
		try{
			comando = " SELECT * FROM ENOC.ESTADISTICA " 
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "
					+ " AND CARGA_ID IN ("+cargas+") "
					+ " AND CODIGO_PERSONAL IN "
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN "
					+ " WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ALUM_PLAN.CODIGO_PERSONAL AND TIPOCAL_ID IN ("+tipos+")))" + orden;			
			
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						Estadistica estadistica = new Estadistica();
						estadistica.mapeaReg(rs);
						lisEstadistica.add(estadistica);
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|lisCambioCarreraPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEstadistica;
	}
	
	public ArrayList<String> listCargasEntreFechas(Connection conn, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<String> lisEstadistica	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando = " SELECT DISTINCT(CARGA_ID) AS CARGA_ID FROM ENOC.ESTADISTICA"					
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " ORDER BY CARGA_ID";			
					rs = st.executeQuery(comando);
					while (rs.next()){				
						lisEstadistica.add(rs.getString("CARGA_ID"));
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNombreColumnas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public String lisCargasEntreFechas(Connection conn, String fechaIni, String fechaFin) throws SQLException{	
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String cargas			= ""; 
		try{
			String comando = " SELECT DISTINCT(CARGA_ID) AS CARGA_ID FROM ENOC.ESTADISTICA"					
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " ORDER BY CARGA_ID";			
					rs = st.executeQuery(comando); 
					int row = 0;
					while (rs.next()){
						row++;
						if (row==1) 
							cargas+="'"+rs.getString("CARGA_ID")+"'"; 
						else
							cargas+=",'"+rs.getString("CARGA_ID")+"'";
					}					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNombreColumnas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cargas;
	}
	
	public ArrayList<String> listModalidadesEntreFechas(Connection conn, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<String> lisEstadistica	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando = " SELECT CARGA_ID, MODALIDAD_ID FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " GROUP BY CARGA_ID, MODALIDAD_ID"
					+ " ORDER BY CARGA_ID, MODALIDAD_ID";
			
					rs = st.executeQuery(comando);
					while (rs.next()){				
						lisEstadistica.add(rs.getString("CARGA_ID")+"$$"+rs.getString("MODALIDAD_ID"));
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNombreColumnas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<String> getListNombreColumnas(Connection conn, String tabla) throws SQLException{
		ArrayList<String> lisEstadistica	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		try{
			comando="SELECT COLUMN_NAME FROM SYS.ALL_TAB_COLUMNS WHERE TABLE_NAME = '"+tabla+"' ";
			
					rs = st.executeQuery(comando);
					while (rs.next()){				
						lisEstadistica.add(rs.getString("COLUMN_NAME"));
					}
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNombreColumnas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> generarReporte(Connection conn, String condicion ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.ESTADISTICA "+condicion;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|generarReporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"+
			"CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, ESTADO, PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<String> getListAlumCarga(Connection conn, String cargaId, String planId, String orden ) throws SQLException{
		
		ArrayList<String> lisAlumnos	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.ESTADISTICA" +
				" WHERE CARGA_ID ='"+cargaId+"' " +
				" AND PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisAlumnos.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListAlumCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumnos;
	}
	
	public ArrayList<String> getListAlumPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<String> lisAlumnos	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, PLAN_ID FROM ENOC.ESTADISTICA" +
				" WHERE PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisAlumnos.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListAlumPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumnos;
	}	
	
	public ArrayList<Estadistica> getListCarrera(Connection conn,String cargaId,String carreraId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA WHERE CARGA_ID='"+cargaId+"' AND CARRERA_ID = '"+carreraId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListReturn (Connection conn, String cargaId1, String cargaId2, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE, "+
			" APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,COTEJADO, "+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, ESTADO, "+
			" PLAN_ID, CARRERA_ID," +
			" FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA " +
			" WHERE CARGA_ID = '"+cargaId1+"' "+
			" AND ESTADO = 'I'"+
			" AND CODIGO_PERSONAL IN " +
			"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId2+"' AND ESTADO = 'I') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListReturn|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListReturnModalidad (Connection conn, String cargaId1, String cargaId2, String modalidades, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE, "+
			" APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,COTEJADO, "+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, ESTADO, "+
			" PLAN_ID, CARRERA_ID," +
			" FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA " +
			" WHERE CARGA_ID = '"+cargaId1+"' "+
			" AND ESTADO = 'I'"+
			" AND CODIGO_PERSONAL IN " +
			"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId2+"' AND ESTADO = 'I') AND MODALIDAD_ID IN ("+modalidades+")"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListReturnModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListNoReturn (Connection conn, String cargaId1, String cargaId2, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{			
			comando = "SELECT" +
					" CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID" +
					" FROM ENOC.ALUM_ESTADO" + 
					" WHERE CARGA_ID = '"+cargaId1+"'" +
					" AND ESTADO = 'I'" +
					" AND CODIGO_PERSONAL NOT IN" +
					" (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO" + 
					" WHERE CARGA_ID = '"+cargaId2+"'" +
					" AND ESTADO = 'I') "+ orden;			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegId(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("CARGA_ID"),rs.getString("BLOQUE_ID"));				
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNoReturn|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
public ArrayList<Estadistica> getListNoReturnModalidad (Connection conn, String cargaId1, String cargaId2,String modalidad, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{			
			comando = "SELECT" +
					" CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID" +
					" FROM ENOC.ALUM_ESTADO" + 
					" WHERE CARGA_ID = '"+cargaId1+"'" +
					" AND ESTADO = 'I'" +
					" AND CODIGO_PERSONAL NOT IN" +
					" (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO" + 
					" WHERE CARGA_ID = '"+cargaId2+"'" +
					" AND ESTADO = 'I') AND MODALIDAD_ID IN ("+modalidad+") "+ orden;			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegId(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("CARGA_ID"),rs.getString("BLOQUE_ID"));				
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNoReturnModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListNew (Connection conn, String cargaId1, String cargaId2, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT" +
			" CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID" +
			" FROM ENOC.ALUM_ESTADO" + 
			" WHERE CARGA_ID = '"+cargaId2+"'" +
			" AND ESTADO = 'I'" +
			" AND CODIGO_PERSONAL NOT IN" +
			" (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO" + 
			" WHERE CARGA_ID = '"+cargaId1+"'" +
			" AND ESTADO = 'I') "+orden;			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegId(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("CARGA_ID"),rs.getString("BLOQUE_ID"));				
				lisEstadistica.add(estadistica);				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNew|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
public ArrayList<Estadistica> getListNewModalidad (Connection conn, String cargaId1, String cargaId2, String modalidad, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT" +
			" CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID" +
			" FROM ENOC.ALUM_ESTADO" + 
			" WHERE CARGA_ID = '"+cargaId2+"'" +
			" AND ESTADO = 'I'" +
			" AND CODIGO_PERSONAL NOT IN" +
			" (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO" + 
			" WHERE CARGA_ID = '"+cargaId1+"'" +
			" AND ESTADO = 'I') AND MODALIDAD_ID IN ("+modalidad+") "+orden;			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegId(conn, rs.getString("CODIGO_PERSONAL"),rs.getString("CARGA_ID"),rs.getString("BLOQUE_ID"));				
				lisEstadistica.add(estadistica);				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListNewModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getLista(Connection conn, String nombre, String paterno, String materno, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, ESTADO, PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA "+
			"WHERE NOMBRE LIKE UPPER('"+nombre+"%') "+
			"AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
			"AND APELLIDO_MATERNO LIKE UPPER('"+materno+"%') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListCumple(Connection conn, String mes, String dia, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, ESTADO, PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA "+
				"WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '"+mes+"' ";					
			if (!dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+dia+"' ";				
			}	
			comando = comando + " "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListCumple|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<String> getListMat (Connection conn, String codigo1, String codigo2, String orden ) throws SQLException{
		
		ArrayList<String> lisEstadistica	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String codigo			= "";
		
		try{
			comando = "SELECT "+
					"DISTINCT(CODIGO_PERSONAL) CODIGO, " +
					"CASE ESTADO WHEN 'I' THEN 'INSCRITO' ELSE 'NO INSCRITO' END ESTADO, "+
					"NOMBRE||'  '||APELLIDO_PATERNO||'  '||APELLIDO_MATERNO  NOMBRE "+
					"FROM ENOC.ESTADISTICA "+
					"WHERE CARGA_ID IN ('03041A','03041B','03042A','03042B','02031A') "+ 
					"AND CODIGO_PERSONAL BETWEEN '"+codigo1+"' AND '"+codigo2+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				codigo = rs.getString("CODIGO")+"~"+rs.getString("ESTADO")+"~"+rs.getString("NOMBRE");
				lisEstadistica.add(codigo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListMat|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getEstadistica(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL)AS CODIGO_PERSONAL, " +
					"E.BLOQUE_ID, E.NOMBRE, E.APELLIDO_PATERNO,E.APELLIDO_MATERNO, " +
					"E.NOMBRE_LEGAL,E.COTEJADO, TO_CHAR(E.F_NACIMIENTO,'DD/MM/YYYY')AS F_NACIMIENTO, " +
					"E.SEXO, E.ESTADO_CIVIL, E.RELIGION_ID, E.PAIS_ID, E.ESTADO_ID,E.CIUDAD_ID, " +
					"E.NACIONALIDAD, E.CURP, E.MODALIDAD_ID, " +
					"E.CLAS_FIN, E.RESIDENCIA_ID, E.ESTADO, E.PLAN_ID, E.CARRERA_ID " +
					"FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A WHERE E.CARGA_ID IN(SELECT CARGA_ID FROM ENOC.CARGA " + 
						"WHERE TO_DATE(now(), 'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL) " +
						"AND A.CODIGO_PERSONAL = E.CODIGO_PERSONAL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaRegEs(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error1 - aca.vista.EstadisticaUtil|getEstadistica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInscripcionFecha(Connection conn, String cargaId, String fInscripcion, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA" +
			" WHERE CARGA_ID IN ('"+cargaId+"')" +
			" AND F_INSCRIPCION <= TO_DATE('"+fInscripcion+"', 'DD/MM/YYYY') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscripcionFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> listInscripcionCargasModalidades(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando =" SELECT"
					+" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "
					+" FROM ENOC.ESTADISTICA"
					+" WHERE CARGA_ID IN ("+cargas+")"
					+" AND MODALIDAD_ID IN ("+modalidades+") "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|listInscripcionCargasModalidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> listInscritosCargasModalidadesFechas(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+") "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|listInscritosCargasModalidadesFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	/**
	 * @author Elifo	 * 
	 * @param cargas Lista de cargas separadas por comas ej.: '07081A','07081B','07081C'
	 * @param orden El orden que se debe aplicar a los datos
	 * @return Un listor de los alumnos repetidos(inscritos en dos carreras) en las cargas seleccionadas
	 * */
	public ArrayList<Estadistica> getListRepetidos(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND CODIGO_PERSONAL IN"
					+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "
					+ " 	WHERE CARGA_ID IN ("+cargas+")"
					+ "		AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ "		GROUP BY CODIGO_PERSONAL" +
			" 	HAVING COUNT(CODIGO_PERSONAL) >= 2) "+ orden;
	
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListRepetidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	/**
	 * @author Elifo	 * 
	 * @param cargas Lista de cargas separadas por comas ej.: '07081A','07081B','07081C'
	 * @param orden El orden que se debe aplicar a los datos
	 * @return Un listor de los alumnos repetidos(inscritos en dos carreras) en las cargas seleccionadas
	 * */
	public ArrayList<Estadistica> getListRepetidos(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"					
					+ " AND CODIGO_PERSONAL IN"
					+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "
					+ " 	WHERE CARGA_ID IN ("+cargas+")"
					+ "		AND MODALIDAD_ID IN ("+modalidades+")"
					+ "		GROUP BY CODIGO_PERSONAL" +
			" 	HAVING COUNT(CODIGO_PERSONAL) >= 2) "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListRepetidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public String getNombre(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			if ( orden.equals("NOMBRE")){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
					"FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}else{
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
					"FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			}	
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<Estadistica> getListEdo(Connection conn, String edoId, String orden ) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA" +
			" WHERE CARGA_ID IN ("+EdoUtil.getCargasEdo(conn, edoId)+") "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	/**
	 * @author Elifo
	 * @param conn Conexixxn a la base de datos
	 * @param orden El orden que se debe aplicar a los datos
	 * @return Un listor de los alumnos extranjeros inscritos
	 * */
	public ArrayList<Estadistica> getListExtranjerosInscritos(Connection conn, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";		
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ('1','4')"+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden; 
			// " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM ARON.EMPLEADO_DEPENDIENTES WHERE MATRICULA IS NOT NULL) " +
			//" AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM  ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO= 5) "+ orden; 
			//System.out.println("COMANDO:"+comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtranjerosInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListExtranjerosInscritosModalidad(Connection conn, String modalidades, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";		
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+")"+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden; 
			// " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM ARON.EMPLEADO_DEPENDIENTES WHERE MATRICULA IS NOT NULL) " +
			//" AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM  ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO= 5) "+ orden; 
			//System.out.println("COMANDO:"+comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtranjerosInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	/*
	 * LISTADO DE EXTRANJEROS INSCRITOS CON MODALIDADES 1 Y 4.
	 * */
	public ArrayList<Estadistica> getListExtInsc(Connection conn, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ('1','4')"+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtInsc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListExtInscModalidad(Connection conn, String modalidades, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+") "+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtInsc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListaExtranjerosInscritosCargaYModalidad(Connection conn, String cargas, String modalidades, String orden) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = "SELECT "+
			" 	CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" 	TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" 	SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" 	CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" 	CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" 	AND MODALIDAD_ID IN ("+modalidades+")"+
			" 	AND CARGA_ID IN ("+cargas+") " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListaExtranjerosInscritosCargaYModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEstadistica;
	}
	
	/**
	 * @author Elifo
	 * @param conn Conexion a la base de datos
	 * @param orden El orden que se debe aplicar a los datos
	 * @return Un listor de los alumnos extranjeros inscritos
	 * */
	public ArrayList<Estadistica> getListExtInscPeriodo(Connection conn, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		String periodo			= "";
		String periodoAnt		= "";
		int year1=0, year2=0;
		
		periodo 	= aca.catalogo.CatPeriodoUtil.getPeriodo(conn);
		year1		= Integer.parseInt(periodo.substring(0,2))-1;
		year2		= Integer.parseInt(periodo.substring(2,4))-1;
		if ( String.valueOf(year1).length()==1) periodoAnt = "0"+String.valueOf(year1); else periodoAnt = String.valueOf(year1);
		if ( String.valueOf(year2).length()==1) periodoAnt += "0"+String.valueOf(year2); else periodoAnt +=String.valueOf(year2);		
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA "+
			"WHERE NACIONALIDAD != 91 " +
			"AND MODALIDAD_ID IN ('1','4')"+
			"AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO IN ('"+periodo+"','"+periodoAnt+"') ) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtInscPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListExtInscPeriodoModalidad(Connection conn, String modalidades, String orden) throws SQLException{
		
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		String periodo			= "";
		String periodoAnt		= "";
		int year1=0, year2=0;
		
		periodo 	= aca.catalogo.CatPeriodoUtil.getPeriodo(conn);
		year1		= Integer.parseInt(periodo.substring(0,2))-1;
		year2		= Integer.parseInt(periodo.substring(2,4))-1;
		if ( String.valueOf(year1).length()==1) periodoAnt = "0"+String.valueOf(year1); else periodoAnt = String.valueOf(year1);
		if ( String.valueOf(year2).length()==1) periodoAnt += "0"+String.valueOf(year2); else periodoAnt +=String.valueOf(year2);		
		
		try{
			comando = "SELECT "+
			"CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA "+
			"WHERE NACIONALIDAD != 91 " +
			"AND MODALIDAD_ID IN ("+modalidades+")"+
			"AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO IN ('"+periodo+"','"+periodoAnt+"') ) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListExtInscPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInternosCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA " +			
			"WHERE CARGA_ID IN ("+cargaId+") " +
			"AND RESIDENCIA_ID ='I' " +
			"AND MODALIDAD_ID IN (1,4)"+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInternosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInternosCargayModalidad(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "
			+ " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
			+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
			+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
			+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
			+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "
			+ " FROM ENOC.ESTADISTICA "
			+ " WHERE CARGA_ID IN ("+cargas+")"
			+ " AND MODALIDAD_ID IN ("+modalidades+")"
			+ " AND RESIDENCIA_ID ='I' "
			+ " AND MODALIDAD_ID IN (1,4)" + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInternosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInscritosCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		//System.out.println("Carga:"+cargaId);
		
		try{
			comando = "SELECT "+
				"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
				"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
				"FROM ENOC.ESTADISTICA " +			
				"WHERE CARGA_ID = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscritosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInscritosCargaSaldo(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		//System.out.println("Carga:"+cargaId);
		
		try{
			comando = "SELECT "+
				"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
				"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID, ENOC.FACULTAD(CARRERA_ID) "+
				"FROM ENOC.ESTADISTICA " +			
				"WHERE CARGA_ID = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscritosCargaSaldo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	
	public ArrayList<Estadistica> getListInscritosCargas(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		//System.out.println("Carga:"+cargaId);
		
		try{
			comando = "SELECT "+
				"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
				"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
				"FROM ENOC.ESTADISTICA " +			
				"WHERE CARGA_ID IN("+cargaId+") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscritosCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListInscritosCargaCarrera(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO," +
				" APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, "+
				" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
				" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
				" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID, ENOC.ALUMNO_CICLO_HIST(CODIGO_PERSONAL, CARGA_ID, PLAN_ID) "+
				" FROM ENOC.ESTADISTICA " +			
				" WHERE CARGA_ID IN ('"+cargaId+"') AND CARRERA_ID ='"+carreraId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscritosCargaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}	
	
	public ArrayList<Estadistica> getListInscritosSE(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA " +			
			"WHERE CARGA_ID IN ("+cargaId+") "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListInscritosSE|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListAlumAnalisis(Connection conn, String periodoId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
				"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA " +			
			"WHERE CARGA_ID LIKE '"+periodoId+"%' " +
			"AND SUBSTR(CARGA_ID,5,1) IN ('1','2','3') "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListAlumAnalisis|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstadistica;
	}
	
	public ArrayList<Estadistica> getListReprobados(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> vecEstadistica	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO," +
				" NOMBRE_LEGAL,COTEJADO,TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
				" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
				" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"+
				" FROM ENOC.ESTADISTICA " +
				" WHERE CARGA_ID IN ("+cargaId+")" +
				" AND  ( SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT "+ 
				" 		WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL"+
				" 		AND SUBSTR(CURSO_CARGA_ID,1,6) = "+cargaId+ 
				" 		AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('3','4','6')"+
				" 		AND TIPOCAL_ID='2') > 0 "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				vecEstadistica.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListReprobados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return vecEstadistica;
	}
	
	public ArrayList<Estadistica> getListPrimerIngreso(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<Estadistica> lisPrimerIngreso	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT * FROM ENOC.ESTADISTICA" +
					" WHERE CARGA_ID = '"+cargaId+"' " +
					" AND CODIGO_PERSONAL||CARGA_ID IN " +
					"( SELECT MATRICULA||CARGA_ID FROM MATEO.FES_CCOBRO " + 
					" WHERE MATRICULA = ESTADISTICA.CODIGO_PERSONAL" +
					" AND PLAN_ID = ESTADISTICA.PLAN_ID" +
					" AND TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID))"+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				lisPrimerIngreso.add(estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListPrimerIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPrimerIngreso;
	}
	
	public static int numInscritosxCarga(Connection conn,String cursoCargaId, String carreraId) throws SQLException{		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int totAlum			= 0;
		
		try{
			comando = " SELECT COUNT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.ESTADISTICA "+
					  " WHERE CARGA_ID = '"+cursoCargaId+"' "+
		 			  " AND CARRERA_ID = '"+carreraId+"' " +
		 			  " AND ESTADO = 'I'";
			rs = st.executeQuery(comando);
			if (rs.next()) totAlum = rs.getInt(1);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|numInscritosxCarga|:"+ex);
		}finally{
			if(rs!=null)rs.close();
			if(st!=null)st.close();
		}
		return totAlum;
	}
	
	
	public ArrayList<Estadistica> getAlumnos(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<String> listaNoRepetidos = new ArrayList<String>();
		ArrayList<Estadistica> lisPrimerIngreso	= new ArrayList<Estadistica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT * FROM ENOC.ESTADISTICA " +
					" WHERE CARGA_ID = '"+cargaId+"' " +
					" AND CODIGO_PERSONAL IN " +
					" ( SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN " +
					" WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL" +
					" AND PLAN_ID = ESTADISTICA.PLAN_ID" +
					" AND PRIMER_MATRICULA = ESTADISTICA.F_INSCRIPCION) "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				if(!listaNoRepetidos.contains(rs.getString("CODIGO_PERSONAL"))){
					listaNoRepetidos.add(rs.getString("CODIGO_PERSONAL"));
					
					Estadistica estadistica = new Estadistica();
					estadistica.mapeaReg(rs);
					lisPrimerIngreso.add(estadistica);
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPrimerIngreso;
	}
	
	public static ArrayList<String> getInscritos(Connection conn, String periodos, String tipos, String orden ) throws SQLException{
		
		ArrayList<String> list		= new ArrayList<String>();
		ArrayList<String> lista 	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String [] arrPeriodos 		= periodos.replaceAll("'","").split(",");
		
		try{
			comando = "SELECT " +
					" SUBSTR(CARGA_ID,1,4) AS PERIODO, COUNT(DISTINCT(CODIGO_PERSONAL)) TOTAL" +
					" FROM ENOC.ALUM_ESTADO " +
					" WHERE ESTADO = 'I' AND SUBSTR(CARGA_ID,1,4) IN( "+periodos+" )" +
					" AND SUBSTR(CARGA_ID,5,6) IN ("+tipos+")" +
					" GROUP BY SUBSTR(CARGA_ID,1,4)" +orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.add(rs.getString("PERIODO")+"$"+rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		for(int i=0; i<arrPeriodos.length; i++){
			String res = arrPeriodos[i]+"$0";
			
			for(String str: list){
				if(str.split("\\$")[0].equals(arrPeriodos[i])){
					res = str;
				}
			}
			
			lista.add(res);
		}
		
		return lista;

	}
	
	public static ArrayList<String> getInscritosCarrera(Connection conn, String periodos, String carreraId, String orden ) throws SQLException{
		
		ArrayList<String> list		= new ArrayList<String>();
		ArrayList<String> lista 	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String [] arrPeriodos 		= periodos.replaceAll("'","").split(",");
		
		try{
			comando = "SELECT SUBSTR(CARGA_ID,1,4) AS PERIODO, SUBSTR(CARGA_ID,6,6) AS TIPO, COUNT(DISTINCT(CODIGO_PERSONAL)) TOTAL" +
					" FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I'" +
					" AND SUBSTR(CARGA_ID,1,4) IN("+periodos+" )" +
					" AND ALUM_CARRERA_HISTORICA(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID)='"+carreraId+"'" +
					" GROUP BY SUBSTR(CARGA_ID,1,4), SUBSTR(CARGA_ID,6,6) "+ orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.add(rs.getString("PERIODO")+"$"+rs.getString("TOTAL")+"-->"+rs.getString("TIPO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getInscritosCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		String [] tipos = "A,B,C,D".split(",");
		for(String tipo : tipos){
			for(int i=0; i<arrPeriodos.length; i++){
				String res = arrPeriodos[i]+"$0-->"+tipo;
				for(String str: list){
					String [] arrList = str.split("\\$");
					if(arrList[0].equals(arrPeriodos[i]) && arrList[1].split("-->")[1].equals(tipo)){
						res = str;
						break;
					}
				}
				lista.add(res);
			}
		}
		
		return lista;

	}
	
	public  HashMap<String, String> mapInscritosPlan(Connection conn, String carreraId, String cargaId) throws SQLException{
		
		HashMap<String, String> map			= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PLAN_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD"
					+ "	FROM ENOC.ESTADISTICA WHERE ESTADO = 'I'"
					+ " AND CARRERA_ID = '"+carreraId+"' AND CARGA_ID= '"+cargaId+"' GROUP BY PLAN_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("PLAN_ID"), rs.getString("CANTIDAD"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|mapInscritosCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	
	
	
	public HashMap<String, Estadistica> getAlumnosCreados(Connection conn, String fechaInicio, String fechaFinal, String fechaLimite, String orden) throws SQLException{
		
		HashMap<String, Estadistica> list		= new HashMap<String,Estadistica>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT" +
					" CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
					" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
					" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
					" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION, 'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
					" FROM ENOC.ESTADISTICA  " +
					" WHERE F_INSCRIPCION<=TO_DATE('"+fechaLimite+"','DD/MM/YYYY') AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL " +
					" WHERE F_CREADO >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND F_CREADO <= TO_DATE('"+fechaFinal+"','DD/MM/YYYY')) " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				
				list.put(rs.getString("CODIGO_PERSONAL"), estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getAlumnosCreados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, Double> getMapPromediosPorCargas(Connection conn, String cargasId) throws SQLException{
		
		HashMap<String, Double> map				= new HashMap<String,Double>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT A.CODIGO_PERSONAL, A.CARGA_ID, TO_CHAR(ENOC.ALUM_PROMEDIO_CARGA(A.CODIGO_PERSONAL, A.CARGA_ID, A.PLAN_ID),'990.00') AS PROMEDIO" +
					" FROM ENOC.ESTADISTICA A WHERE CARGA_ID IN ("+cargasId+") ORDER BY CODIGO_PERSONAL, CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CARGA_ID"), Double.parseDouble(rs.getString("PROMEDIO")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getMapPromediosPorCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String, Integer> getAumnosxCarrera(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, Integer> lisAlumCarrera = new HashMap<String, Integer>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARRERA_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS CANTIDAD " +
					" FROM ENOC.ESTADISTICA WHERE ESTADO = 'I' " +
					" AND CARGA_ID = '"+cargaId+"' GROUP BY CARRERA_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisAlumCarrera.put(rs.getString("CARRERA_ID"), Integer.parseInt(rs.getString("CANTIDAD")));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getAumnosxCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumCarrera;
	}
	
	
	public HashMap<String, Estadistica> getAlumno(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, Estadistica> map = new HashMap<String, Estadistica>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
					"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "+
					"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
					"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
					"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
					"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
					"FROM ENOC.ESTADISTICA " +			
					"WHERE CARGA_ID = '"+cargaId+"' ";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				Estadistica estadistica = new Estadistica();
				estadistica.mapeaReg(rs);
				map.put(rs.getString("CARRERA_ID"), estadistica);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getAumnosxCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapIngresoFac(Connection conn, String cargaId, String modalidades, String estado, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")";
			
			if (tipo.equals("UM")) 
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'" 
					+ " GROUP BY FACULTAD_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("FACULTAD_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapIngresoFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapIngresoFac(Connection conn, String cargaId, String modalidades, String fechaIni, String fechaFin, String estado, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			
			if (tipo.equals("UM")) 
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'" 
					+ " GROUP BY FACULTAD_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("FACULTAD_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapIngresoFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapIngresoCarrera(Connection conn, String cargaId, String modalidades, String estado, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARRERA_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")";
			
			if (tipo.equals("UM"))
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'"
					+ " GROUP BY CARRERA_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CARRERA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapIngresoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapIngresoCarrera(Connection conn, String cargaId, String modalidades, String fechaIni, String fechaFin, String estado, String tipo) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CARRERA_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			
			if (tipo.equals("UM")) 
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'"
					+ " GROUP BY CARRERA_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("CARRERA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapIngresoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaInscEstadoyCarrera(Connection conn, String cargaId, String modalidades, String estado) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, CARRERA_ID, COALESCE(COUNT(*),0) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = '"+estado+"'"
					+ " GROUP BY PAIS_ID, ESTADO_ID, CARRERA_ID";	
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("PAIS_ID")+rs.getString("ESTADO_ID")+rs.getString("CARRERA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapaInscritosPorEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaInscEstado(Connection conn, String cargaId, String modalidades, String estado) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, COALESCE(COUNT(*),0) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = '"+estado+"'"
					+ " GROUP BY PAIS_ID, ESTADO_ID";	
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("PAIS_ID")+rs.getString("ESTADO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapaInscritosPorEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaModalidadesEnCargas(Connection conn, String cargas ) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MODALIDAD_ID, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN("+cargas+") GROUP BY MODALIDAD_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("MODALIDAD_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapaModalidadesEnCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapAlumnosPorCarga(Connection conn, String fechaIni, String fechaFin ) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CARGA_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapAlumnosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public HashMap<String, String> mapAlumnosModalidadPorCarga(Connection conn, String fechaIni, String fechaFin, String carga ) throws SQLException{
		
		HashMap<String, String> map = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT MODALIDAD_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND CARGA_ID = '"+carga+"'"
					+ " GROUP BY MODALIDAD_ID";	
			rs = st.executeQuery(comando);
			while(rs.next()){				
				map.put(rs.getString("MODALIDAD_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|mapAlumnosModalidadPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public String getAlumBetween(Connection conn, String cargaId, String planId, String edadInicio, String edadFinal ) throws SQLException{
		String total 			= "-";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE  TRUNC(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), F_NACIMIENTO)/12) BETWEEN "+edadInicio+" AND "+edadFinal
					+ " AND PLAN_ID='"+planId+"' AND CARGA_ID ='"+cargaId+"' ";			
			rs = st.executeQuery(comando);						
			while (rs.next()){
				
				total = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getAlumBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	
	public String getNumGeneroPorPlan(Connection conn, String genero, String planId, String cargaId ) throws SQLException{
		String total 			= "-";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE SEXO = '"+genero+"' "
					+ " AND PLAN_ID='"+planId+"' AND CARGA_ID ='"+cargaId+"'";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				total = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getNumGeneroPorPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public String getNumExtranjeros(Connection conn, String planId, String cargaId ) throws SQLException{
		String total 			= "-";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE NACIONALIDAD != '91'"
					+ " AND PLAN_ID='"+planId+"' AND CARGA_ID ='"+cargaId+"'";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				total = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getNumExtranjeros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	
	
/*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			System.out.println("Inicio..!");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			System.out.println("Procesando..!");
			EstadisticaUtil eu = new EstadisticaUtil();
			ArrayList lis = new ArrayList();  
			lis = eu.getEstadistica(Conn,"ORDER BY CARRERA_ID, NOMBRE");			
			System.out.println("vECTOR="+lis.size());
			Conn.commit();
			Conn.close();
			System.out.println("Fin..!");
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
*/
	
}