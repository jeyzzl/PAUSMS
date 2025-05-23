package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesCcobroUtil {	
	
	// Determina el saldo vencido del contrato financiero
	public static String getFechaInscAnt(Connection conn, String codigoPersonal, String fecha) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		String fechaInsc 		= "";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(MAX(FECHA),'DD/MM/YYYY'), '"+fecha+"') AS ULTIMAFECHA FROM MATEO.FES_CCOBRO " +
				"WHERE MATRICULA = ? AND INSCRITO = 'S' AND FECHA != TO_DATE(?, 'DD/MM/YYYY')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, fecha);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				fechaInsc = rs.getString("ULTIMAFECHA");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getFechaInsc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		
		return fechaInsc;
	}
	
	public static String getCarreraAnt(Connection conn, String codigoPersonal, String fecha) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		String fechaInsc 		= "";		
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM MATEO.FES_CCOBRO " +
				"WHERE MATRICULA = ? AND INSCRITO = 'S' AND FECHA = TO_DATE(?, 'DD/MM/YYYY')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, fecha);
		
			rs= ps.executeQuery();	
			if(rs.next()){
				fechaInsc = rs.getString("CARRERA_ID");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|getCarreraAnt|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}		
		
		return fechaInsc;
	}
	
	public ArrayList<String> getListaAlumnos(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		
		ArrayList<String> alumnos		= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MATRICULA, SEMESTRE FROM MATEO.FES_CCOBRO WHERE CARGA_ID ='"+cargaId+"' AND CARRERA_ID='"+carreraId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				String alum = rs.getString("MATRICULA")+"/"+rs.getString("SEMESTRE");
				alumnos.add(alum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getListaAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return alumnos;
	}
	
	public static boolean getIncritoEnLinea(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		boolean inscrito 	= false;
		
		try{
			String comando = "SELECT ON_LINE FROM MATEO.FES_CCOBRO WHERE MATRICULA = '"+matricula+"'" +
							" AND CARGA_ID ='"+cargaId+"' AND BLOQUE='"+bloqueId+"' AND INSCRITO = 'S' AND ON_LINE = 'S'";
			
			rs = st.executeQuery(comando);
			if(rs.next()) inscrito = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getIncritoEnLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return inscrito;
	}
	
	public ArrayList<FesCcobro> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<FesCcobro> lista 	= new ArrayList<FesCcobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD,"
					+ " TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID,"
					+ " FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO"
					+ " FROM MATEO.FES_CCOBRO "+orden;
			rs = st.executeQuery(comando);
			//System.out.println(comando);
			while (rs.next()){
				FesCcobro acceso = new FesCcobro();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<FesCcobro> listCalculosAlumno(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<FesCcobro> lista 	= new ArrayList<FesCcobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD,"
					+ " TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'YYYY-MM-DD') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID,"
					+ " FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO"
					+ " FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = '"+codigoPersonal+"'"
					+ " AND MATRICULA||CARGA_ID||BLOQUE IN (SELECT DISTINCT(CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') "
	 				+ orden;
			rs = st.executeQuery(comando);			
			while (rs.next()){
				FesCcobro acceso = new FesCcobro();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|listCalculosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public static HashMap<String, FesCcobro> getListCompara(Connection conn, String carga1, String carga2, String opcion)throws SQLException{
		HashMap<String, FesCcobro> mapHistorico = new HashMap<String, FesCcobro>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String texto 		= ""; 
		String comando	= "";
		
		try{
			// Opcion 1 busca los alumnos que regresaron
			// Opcion 2 y 3 Busca alumnos que no regresaron y los nuevos
			if (!opcion.equals("1")) texto = "NOT"; else texto = "";
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE INSCRITO = 'S'" +
 					" AND MATRICULA||CARGA_ID||BLOQUE IN" +
 					"	(SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID " +
 					"	FROM ENOC.ALUM_ESTADO" +
 					"	WHERE CARGA_ID = '"+carga1+"'" +
 					"	AND ESTADO = 'I'" +
 					"	AND CODIGO_PERSONAL "+texto+" IN" +
 					"		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+carga2+"' AND ESTADO = 'I'))";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				FesCcobro historico = new FesCcobro();
				historico.mapeaReg(rs);
				mapHistorico.put(historico.getMatricula()+historico.getCargaId()+historico.getBloque(), historico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getListHistorico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapHistorico;
	}
	
	public static HashMap<String, FesCcobro> getListComparaModalidad(Connection conn, String carga1, String carga2, String modalidad, String opcion)throws SQLException{
		HashMap<String, FesCcobro> mapHistorico = new HashMap<String, FesCcobro>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String texto 		= ""; 
		String comando	= "";
		
		try{
			// Opcion 1 busca los alumnos que regresaron
			// Opcion 2 y 3 Busca alumnos que no regresaron y los nuevos
			if (!opcion.equals("1")) texto = "NOT"; else texto = "";
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE INSCRITO = 'S'" +
 					" AND MATRICULA||CARGA_ID||BLOQUE IN" +
 					"	(SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID " +
 					"	FROM ENOC.ALUM_ESTADO" +
 					"	WHERE CARGA_ID = '"+carga1+"'" +
 					"	AND ESTADO = 'I'" +
 					"	AND CODIGO_PERSONAL "+texto+" IN" +
 					"		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+carga2+"' AND ESTADO = 'I')) AND MODALIDAD_ID IN ("+modalidad+")";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				FesCcobro historico = new FesCcobro();
				historico.mapeaReg(rs);
				mapHistorico.put(historico.getMatricula()+historico.getCargaId()+historico.getBloque(), historico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getListHistorico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapHistorico;
	}
	
	public static HashMap<String, FesCcobro> getMapInscritos(Connection conn, String cargaId )throws SQLException{
		HashMap<String, FesCcobro> mapHistorico = new HashMap<String, FesCcobro>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE CARGA_ID = '"+cargaId+"'"+
 					" AND INSCRITO = 'S'";		
			rs = st.executeQuery(comando);
			while(rs.next()){
				FesCcobro historico = new FesCcobro();
				historico.mapeaReg(rs);
				mapHistorico.put(historico.getMatricula()+historico.getCargaId()+historico.getBloque(), historico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getListAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapHistorico;
	}
	
	public static HashMap<String, FesCcobro> mapInscritosCargas(Connection conn, String cargas )throws SQLException{
		HashMap<String, FesCcobro> mapHistorico = new HashMap<String, FesCcobro>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE CARGA_ID IN("+cargas+")"+
 					" AND INSCRITO = 'S'";		
			rs = st.executeQuery(comando);
			while(rs.next()){
				FesCcobro historico = new FesCcobro();
				historico.mapeaReg(rs);
				mapHistorico.put(historico.getMatricula()+historico.getCargaId()+historico.getBloque(), historico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getMapInscritosCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapHistorico;
	}
	
	public static String getFecha (Connection conn, String codigoPersonal, String cargaId)throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		String fecha	= "";
		
		try{			
			comando = "SELECT TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
 					" FROM MATEO.FES_CCOBRO" +
 					" WHERE MATRICULA = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"'";
			rs = st.executeQuery(comando);
			while(rs.next()){
				fecha = rs.getString("FECHA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return fecha;
	}
	
	/* BUSCA LA LISTA DE ALUMNOS QUE INGRESARON EN UNA CARGA ACADEMICA */  
	public ArrayList<FesCcobro> getAlumnosCargaPlan(Connection conn, String cargaId, String facultadId, String planId, String orden) throws SQLException{
		ArrayList<FesCcobro> lista 	= new ArrayList<FesCcobro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "	SELECT " +
					" MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
					" FROM MATEO.FES_CCOBRO MCC" +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" AND PLAN_ID = '"+planId+"'"+
					" AND CARRERA_ID IN" +
					" 	(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')" +
					" AND INSCRITO = 'S'" +
					" AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesCcobro acceso = new FesCcobro();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getAlumnosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	/* BUSCA EL PROMEDIO DE LA LISTA DE ALUMNOS QUE INGRESARON EN UNA CARGA ACADEMICA */
	public HashMap<String, String> getPromedioAlumno(Connection conn, String cargaId, String facultadId) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		try{
			comando = " SELECT MATRICULA, PLAN_ID, ALUM_PROMEDIO(MATRICULA,PLAN_ID) AS PROMEDIO" +
					  "	FROM MATEO.FES_CCOBRO MCC" +
					  "	WHERE CARGA_ID = '"+cargaId+"'" +
					  "	AND CARRERA_ID IN" +
					  "	(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')" +
					  "	AND INSCRITO = 'S'" +
					  "	AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				String promedio = "";
				promedio = rs.getString("PROMEDIO");
				llave = rs.getString("MATRICULA")+","+rs.getString("PLAN_ID");
				mapa.put(llave, promedio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getPromedioAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	/* BUSCA EL SEMESTRE O TETRAMESTRE DEL ALUMNO EN UNA CARGA ACADEMICA */
	public static HashMap<String, String> mapAlumCicloEnCarga(Connection conn, String cargaId ) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, COALESCE(SEMESTRE,0) AS CICLO" +
					  "	FROM MATEO.FES_CCOBRO MCC" +
					  "	WHERE CARGA_ID = '"+cargaId+"'" +					  
					  "	AND INSCRITO = 'S'"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				String ciclo = rs.getString("CICLO");
				llave = rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE");
				mapa.put(llave, ciclo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getPromedioAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	/* BUSCA LAS CARGAS ACADEMICAS DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public ArrayList<String> getCargaIdIngreso(Connection conn, String facultadId, String orden) throws SQLException{
		ArrayList<String> lista 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "	SELECT DISTINCT(CARGA_ID) AS CARGA_ID" +
					  " FROM MATEO.FES_CCOBRO MCC" +
					  "	WHERE CARRERA_ID IN" +
					  "	(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')" +
					  "	AND INSCRITO = 'S'" +
					  "	AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)" +
					  "	AND SUBSTR(CARGA_ID,1,2)>'04' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("CARGA_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getCargaIdIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	/* BUSCA LAS CARRERAS POR LA CARGA ACADEMICA DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public ArrayList<String> lisCarrerasPorCarga(Connection conn, String cargaId, String facultadId, String orden) throws SQLException{
		ArrayList<String> lista 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "	SELECT DISTINCT(CARRERA_ID) AS CARRERA_ID" +
					  " FROM MATEO.FES_CCOBRO MCC" +
					  "	WHERE CARGA_ID = '"+cargaId+"'" +
					  " AND CARRERA_ID IN" +
					  "	(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')" +
					  "	AND INSCRITO = 'S'" +
					  "	AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)" +
					  "	AND SUBSTR(CARGA_ID,1,2)>'04' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				lista.add(rs.getString("CARRERA_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|lisCarrerasPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	/* BUSCA LOS PLANES DE LAS CARRERAS POR LA CARGA ACADEMICA DE UNA FACULTAD DONDE INGRESARON ALUMNOS */
	public ArrayList<String> lisPlanPorCarga(Connection conn, String cargaId, String facultadId, String orden) throws SQLException{
		ArrayList<String> list 	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "	SELECT DISTINCT(PLAN_ID) AS PLAN_ID, CARRERA_ID" +
					" FROM MATEO.FES_CCOBRO MCC" +
					" WHERE CARGA_ID = '"+cargaId+"' " +
					" AND CARRERA_ID IN" +
					"  (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')" +
					" AND INSCRITO = 'S'" +
					" AND FECHA = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = MCC.MATRICULA AND PLAN_ID = MCC.PLAN_ID)" +
					" AND SUBSTR(CARGA_ID,1,2)>'04' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.add( rs.getString("PLAN_ID")+","+rs.getString("CARRERA_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|lisPlanPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return list;
	}

	public ArrayList<String> getTotalCargasAlumno(Connection conn, String matricula, String carreraId ) throws SQLException{
		
		ArrayList<String> cargas	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT DISTINCT(CARGA_ID) AS CARGA FROM MATEO.FES_CCOBRO WHERE MATRICULA = '"+matricula+"' AND CARRERA_ID = '"+carreraId+"' ORDER BY 1";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				cargas.add(rs.getString("CARGA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobroUtil|getTotalCargasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return cargas;
	}
	
}