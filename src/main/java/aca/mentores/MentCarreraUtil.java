/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

	/**
	 * @author Elifo
	 *
	 */
	public class MentCarreraUtil {
	//Cuando uses este metodo, porfavor borra este comentario para saber que ya se uso
	public ArrayList<MentCarrera> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentCarrera> lisMentCarrera	= new ArrayList<MentCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARRERA_ID, MENTOR_ID, PERIODO_ID" +
					" FROM ENOC.MENT_CARRERA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentCarrera mentCarrera = new MentCarrera();
				mentCarrera.mapeaReg(rs);
				lisMentCarrera.add(mentCarrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentCarrera;
	}
	
	public ArrayList<MentCarrera> getListCarrera(Connection conn, String carreraId, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentCarrera> lisMentCarrera	= new ArrayList<MentCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARRERA_ID, MENTOR_ID, PERIODO_ID" +
					" FROM ENOC.MENT_CARRERA" + 
					" WHERE CARRERA_ID = '"+carreraId+"'" +
					" AND PERIODO_ID = '"+periodoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentCarrera mentCarrera = new MentCarrera();
				mentCarrera.mapeaReg(rs);
				lisMentCarrera.add(mentCarrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentCarrera;
	}
	
	public ArrayList<String> listMentorFacultad(Connection conn, String periodoId, String facultadId, String fecha, String orden ) throws SQLException{
		
		ArrayList<String> lisMentCarrera	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID FROM ENOC.MENT_CARRERA"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'"
					+ " AND MENTOR_ID IN "
					+ " 	(SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodoId+"' "
					+ "		AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL ) " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisMentCarrera.add(rs.getString("MENTOR_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentCarrera;
	}
	
	public ArrayList<String> listMentoresPeriodo(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<String> lisMentCarrera	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID FROM ENOC.MENT_CARRERA"
					+ " WHERE PERIODO_ID = '"+periodoId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisMentCarrera.add(rs.getString("MENTOR_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarreraUtil|listMentoresPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentCarrera;
	}
	
	public ArrayList<String> getCarrerasMentor(Connection conn, String mentor, String periodo, String orden ) throws SQLException{
			
		ArrayList<String> lisAsambleas	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT FACULTAD_NOMBRE_CORTO(ENOC.FACULTAD(CARRERA_ID)) AS FACULTAD, "
					+ " ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA,  EMP_NOMBRE(MENTOR_ID) AS NOMBRE_EMPLEADO"
					+ " FROM ENOC.MENT_CARRERA WHERE MENTOR_ID = '"+mentor+"' AND PERIODO_ID = '"+periodo+"'"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisAsambleas.add(rs.getString("FACULTAD")+"@@"+rs.getString("CARRERA")+"@@"+rs.getString("NOMBRE_EMPLEADO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentCarreraUtil|getCarrerasMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisAsambleas;
	}
		
}