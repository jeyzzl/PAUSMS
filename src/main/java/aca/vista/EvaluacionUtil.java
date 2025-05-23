// Clase para la vista ALUMNO_EVALUACION
package  aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class EvaluacionUtil{
	
	public ArrayList<AlumnoEvaluacion> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<AlumnoEvaluacion> lisEvaluacion	= new ArrayList<AlumnoEvaluacion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, "+
				"CODIGO_PERSONAL, "+
				"EVALUACION_ID, "+
				"NOMBRE_EVALUACION, "+
				"ESTRATEGIA_ID, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "+
				"VALOR, "+
				"TIPO, "+
				"NOTA "+
				"FROM ENOC.ALUMNO_EVALUACION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoEvaluacion evaluacion = new AlumnoEvaluacion();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EvaluacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}
	
	public ArrayList<AlumnoEvaluacion> getListCurso(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<AlumnoEvaluacion> lisEvaluacion	= new ArrayList<AlumnoEvaluacion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, "+
				"CODIGO_PERSONAL, "+
				"EVALUACION_ID, "+
				"NOMBRE_EVALUACION, "+
				"ESTRATEGIA_ID, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "+
				"VALOR, "+
				"TIPO, "+
				"NOTA "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				" "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoEvaluacion evaluacion = new AlumnoEvaluacion();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EvaluacionUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}	

}