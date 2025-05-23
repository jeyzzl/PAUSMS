
package aca.kardex;

import java.sql.*;
import java.util.ArrayList;

public class ActividadUtil{
		
	public ArrayList<KrdxAlumnoActiv> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoActiv> lisActiv		= new ArrayList<KrdxAlumnoActiv>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA "+
				"FROM ENOC.KRDX_ALUMNO_ACTIV "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoActiv actividad = new KrdxAlumnoActiv();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}
	
	public ArrayList<KrdxAlumnoActiv> getLista(Connection conn, String codigoPersonal, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoActiv> lisActiv	= new ArrayList<KrdxAlumnoActiv>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA "+
					"FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					"AND CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoActiv actividad = new KrdxAlumnoActiv();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}	
	
	
	public String getAlumnoEvaluacion(Connection conn, String codigoPersonal, String actividadId ) throws SQLException{
				
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String evaluacion = "-";
		
		try{
			comando = "SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND ACTIVIDAD_ID = TO_NUMBER('"+actividadId+"','9999999')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getString("NOTA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getAlumnoEvaluacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}	

}