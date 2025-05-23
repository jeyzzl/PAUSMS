// Clase Util para la tabla de Carga
package aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CargaAcaUtil{
		
	public ArrayList<CargaAcademica> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga		= new ArrayList<CargaAcademica>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT "
					+ " CURSO_CARGA_ID, CARGA_ID, "
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "
					+ " MODALIDAD_ID, "
					+ " ESTADO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "
					+ " CICLO, "
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS, "
					+ " HT, "
					+ " HP, "
					+ " HI, "
					+ " TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "
					+ " FROM ENOC.CARGA_ACADEMICA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getMatReprobadas(Connection conn, String cargaId, String modalidadId ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga		= new ArrayList<CargaAcademica>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID IN("+cargaId+") AND MODALIDAD_ID IN("+modalidadId+") AND ORIGEN = 'O' "
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN("+cargaId+") "
					+ " AND TIPOCAL_ID IN ('2','4'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getMatReprobadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListaCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListaCargasyModalidades(Connection conn, String cargas, String modalidades, String tipo, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ORIGEN = '"+tipo+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaCargasyModalidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	// Lista de materias de una facultad 
	public ArrayList<CargaAcademica> listaCargaFacultad(Connection conn, String cargaId, String facultadId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'"
				+ " "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|listaCargaFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListaCarrera(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga		= new ArrayList<CargaAcademica>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+
				"AND CARRERA_ID = '"+carreraId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisCarga;
	}	
	
	public ArrayList<CargaAcademica> getListaMaestro(Connection conn, String cargaId, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.99')) AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+				
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND ORIGEN = 'O' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListSinAlumno(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID, ESTADO, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA"
					+ " FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID NOT IN"
					+ " 	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = CARGA_ACADEMICA.CURSO_CARGA_ID) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListSinAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListaMaestro(Connection conn, String cargaId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga	= new ArrayList<CargaAcademica>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+ 
				"CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, "+ 
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+ 
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+ 
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+ 
				"HT, HP, HI, "+
				"NOTA_APROBATORIA," +
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = '"+cargaId+"' "+ 
				"AND ORIGEN = 'O' "+
				"AND CURSO_CARGA_ID  IN (SELECT CURSO_CARGA_ID "+ 
						"FROM ENOC.KRDX_CURSO_ACT "+ 
						"WHERE CURSO_CARGA_ID = CARGA_ACADEMICA.CURSO_CARGA_ID )"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<CargaAcademica> getListaCargaCarrera(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		ArrayList<CargaAcademica> lisCarga		= new ArrayList<CargaAcademica>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+
				"AND CARRERA_ID = '"+carreraId+"' AND ORIGEN = 'O' " +
				"AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE TIPOCAL_ID IN('I','1')) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaCargaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisCarga;
	}
	
	public static ArrayList<String> getListaCursoIdPorCondicion(Connection conn, String condiciones) throws SQLException{
		ArrayList<String> lista = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT DISTINCT(A.CURSO_ID) FROM ENOC.CARGA_ACADEMICA A "+condiciones;
		
			rs = st.executeQuery(comando); 
			while(rs.next()){
				lista.add(rs.getString("CURSO_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getListaCursoIdPorCondicion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public static ArrayList<CargaAcademica> getMateriasPorCarga(Connection conn, String codigoPersonal, String cargaId, String orden) throws SQLException{
		ArrayList<CargaAcademica> lista = new ArrayList<CargaAcademica>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT * FROM ENOC.CARGA_ACADEMICA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID IN ("+cargaId+") AND ORIGEN = 'O' "+orden;
		
			rs = st.executeQuery(comando); 
			while(rs.next()){
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);
				lista.add(carga);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|getMateriasPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}	
	
	public HashMap<String,String> mapMatPorMaestro(Connection conn, String cargas, String modalidades) throws SQLException{
		
		HashMap<String,String> mapa		= new HashMap<String, String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando	             	= "";	
		
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(*) AS TOTAL FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " GROUP BY CODIGO_PERSONAL"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|mapMatPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapCreditosPorMaestro(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> mapa		= new HashMap<String, String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando	             	= "";	
		
		try{
			comando = " SELECT CODIGO_PERSONAL, SUM(CREDITOS) AS TOTAL FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CARGA_ID = '"+cargas+"'"					
					+ " GROUP BY CODIGO_PERSONAL"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcaUtil|mapCreditosPorMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}

	/*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Statement st 		= Conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";			
			
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.9') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA "+				
				"FROM ENOC.CARGA_ACADEMICA ORDER BY 4"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaAcademica carga = new CargaAcademica();
				carga.mapeaReg(rs);				
				System.out.println(carga.getCodigoPersonal()+" "+carga.getCargaId());
			}
			
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
			
			Conn.commit();
			Conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}	
	*/
}