package aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlumnoEficienciaUtil {
	
	public ArrayList<AlumnoEficiencia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumnoEficiencia> lisAlumnoEficiencia	= new ArrayList<AlumnoEficiencia>();
		Statement st 									= conn.createStatement();
		ResultSet rs 									= null;
		String comando									= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CURSO_CARGA_ID,"+
						"EVALUACION_ID, TIPO, VALOR, NOTA, "+
						"EVALUADAS " +
						"FROM ENOC.ALUMNO_EFICIENCIA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoEficiencia alumnoEficiencia = new AlumnoEficiencia();
				alumnoEficiencia.mapeaReg(rs);
				lisAlumnoEficiencia.add(alumnoEficiencia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficienciaxUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlumnoEficiencia;
	}
	
	public HashMap<String, AlumnoEficiencia> mapaMateria(Connection conn, String cursoCargaId) throws SQLException{		
		HashMap<String, AlumnoEficiencia> mapa = new HashMap<String, AlumnoEficiencia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, TIPO, VALOR, NOTA, EVALUADAS, PUNTOS, TOT_ACTIVIDADES FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumnoEficiencia eficiencia = new AlumnoEficiencia();
				eficiencia.mapeaReg(rs);
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("EVALUACION_ID"), eficiencia);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficienciaUtil|mapPuntosEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapPuntosEval(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID,"
					+ " CASE WHEN COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) = 0 THEN 1 ELSE COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) END AS PUNTOS_EVAL"
					+ " FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID"), rs.getString("PUNTOS_EVAL"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficienciaUtil|mapPuntosEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapPuntosEvaluados(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CURSO_CARGA_ID, CODIGO_PERSONAL, COALESCE(SUM(VALOR*(EVALUADAS/100)),0) AS PUNTOS "
					+ " FROM ENOC.ALUMNO_EFICIENCIA WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' GROUP BY CURSO_CARGA_ID, CODIGO_PERSONAL";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID"), rs.getString("PUNTOS"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficienciaUtil|mapPuntosEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapPuntosEvaluadosMateria(Connection conn, String cursoCargaId) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, COALESCE(SUM(VALOR*(EVALUADAS/100)),0) AS PUNTOS"
					+ " FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' GROUP BY CODIGO_PERSONAL";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("PUNTOS"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficienciaUtil|mapPuntosEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapPuntosAlumno(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CURSO_CARGA_ID, CODIGO_PERSONAL, COALESCE(SUM(VALOR*(NOTA/100)),0) AS PUNTOS "
					+ " FROM ENOC.ALUMNO_EFICIENCIA WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' GROUP BY CURSO_CARGA_ID, CODIGO_PERSONAL";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID"), rs.getString("PUNTOS"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.ActualUtil|mapPuntosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}


}