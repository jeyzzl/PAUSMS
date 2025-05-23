/**
 * 
 */
package aca.vista;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class MenCarreraUtil {
	public ArrayList<MenCarrera> getListCarrera(Connection conn, String carreraId, String orden ) throws SQLException{		
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	
		try{
			comando = "SELECT PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE, MENTOR_ID, MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE CARRERA_ID = '"+carreraId+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MenCarrera mentor = new MenCarrera();
				mentor.mapeaReg(rs);
				lisCarMentor.add(mentor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCarMentor;	
	}
	// LISTADO DE MENTORES EN GENERAL
	public ArrayList<MenCarrera> getLista(Connection conn, String orden ) throws SQLException{		
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID, MENTOR_NOMBRE," +
					" FACULTAD_ID, FACULTAD_NOMBRE,"+
					" CARRERA_ID, CARRERA_NOMBRE, PERIODO_ID FROM ENOC.MEN_CARRERA "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MenCarrera mentor = new MenCarrera();
				mentor.mapeaReg(rs);
				lisCarMentor.add(mentor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCarMentor;	
	}
	
	public ArrayList<MenCarrera> getListPeriodo(Connection conn, String periodoId, String orden ) throws SQLException{		
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
	
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = '"+periodoId+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				// Agrega los mentores a la lista una sola vez por facultad	
				MenCarrera mentor = new MenCarrera();
				mentor.mapeaReg(rs);
				lisCarMentor.add(mentor);					
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
	
		return lisCarMentor;	
	}
	
	public ArrayList<MenCarrera> getListMentPeriodo(Connection conn, String periodoId, String orden ) throws SQLException{	
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		String mentorId		= "";
	
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = '"+periodoId+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				// Agrega los mentores a la lista una sola vez por facultad
				if (!mentorId.equals(rs.getString("MENTOR_ID"))){
					mentorId = rs.getString("MENTOR_ID");
					MenCarrera mentor = new MenCarrera();
					mentor.mapeaReg(rs);
					lisCarMentor.add(mentor);
				}	
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
	
		return lisCarMentor;	
	}	
	
	
	public ArrayList<MenCarrera> getListMentPeriodo(Connection conn, String periodoId, String facultadId, String orden ) throws SQLException{	
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		String mentorId		= "";
	
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = '"+periodoId+"' " +
				" and FACULTAD_ID='"+facultadId+"'"+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				// Agrega los mentores a la lista una sola vez por facultad
				if (!mentorId.equals(rs.getString("MENTOR_ID"))){
					mentorId = rs.getString("MENTOR_ID");
					MenCarrera mentor = new MenCarrera();
					mentor.mapeaReg(rs);
					lisCarMentor.add(mentor);
				}	
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
	
		return lisCarMentor;	
	}	
	
	public ArrayList<MenCarrera> getListMentPerFecha(Connection conn, String periodoId, String fecha, String orden ) throws SQLException{	
		ArrayList<MenCarrera> lisCarMentor	= new ArrayList<MenCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		String mentorId		= "";
	
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = '"+periodoId+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				// Agrega los mentores a la lista una sola vez por facultad
				if (!mentorId.equals(rs.getString("MENTOR_ID"))){
					mentorId = rs.getString("MENTOR_ID");
					MenCarrera mentor = new MenCarrera();
					mentor.mapeaReg(rs);
					lisCarMentor.add(mentor);
				}	
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarreraUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
	
		return lisCarMentor;	
	}
	
}