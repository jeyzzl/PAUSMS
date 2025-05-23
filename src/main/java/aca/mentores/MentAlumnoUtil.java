/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Elifo
 *
 */
public class MentAlumnoUtil {
	public ArrayList<MentAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO" +
					" FROM ENOC.MENT_ALUMNO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListMentorPeriodo(Connection conn, String mentorId, String periodoId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE MENTOR_ID = '"+mentorId+"'"					
					+ " AND PERIODO_ID = '"+periodoId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentorPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListActivos(Connection conn, String mentorId, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO " +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE MENTOR_ID = '"+mentorId+"'" +
					" AND PERIODO_ID = '"+periodoId+"'" +
					" AND STATUS = 'A' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListActivosEnFecha(Connection conn, String mentorId, String periodoId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO " +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE MENTOR_ID = '"+mentorId+"'" +
					" AND PERIODO_ID = '"+periodoId+"'" +
					" AND TO_DATE('"+fecha+"') BETWEEN ENOC.MENT_ALUMNO.FECHA_INICIO AND ENOC.MENT_ALUMNO.FECHA_FINAL" +
					" AND STATUS = 'A' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListMentPeriodo(Connection conn, String periodoId, String orden ) throws SQLException{
			
			ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO" +
						" FROM ENOC.MENT_ALUMNO" + 
						" WHERE PERIODO_ID = '"+periodoId+"'" +
						" AND STATUS = 'A' "+ orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					MentAlumno mentor = new MentAlumno();
					mentor.mapeaReg(rs);
					lisMentAlumno.add(mentor);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentPeriodo|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisMentAlumno;
		}
	
	public ArrayList<MentAlumno> getListAlumMentor(Connection conn, String periodoId, String mentorId, String facultadId, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = '"+periodoId+"'" +
					" AND MENTOR_ID = '"+mentorId+"'" +
					" AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'" +
					" AND STATUS = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListAlumMentor(Connection conn, String periodoId, String mentorId, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND MENTOR_ID = '"+mentorId+"'"
					+ " AND STATUS = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	// Lista de alumnos aconsejados en un periodo y fecha
	public ArrayList<MentAlumno> listAlumMentor(Connection conn, String periodoId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND STATUS = 'A'"
					+ " AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|listAlumMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> listAlumMentorVigente(Connection conn, String periodoId, String mentorId, String facultadId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = '"+periodoId+"'" +
					" AND MENTOR_ID = '"+mentorId+"'" +
					" AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL" +
					" AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'" +
					" AND STATUS = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListAlumCoordinador(Connection conn, String periodoId, String codigoId, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "	SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND STATUS = 'A'"
					+ " AND CARRERA_ID"
					+ " IN (SELECT CARRERA_ID"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CODIGO_PERSONAL = '"+codigoId+"'"
					+ " AND CAT_CARRERA.CARRERA_ID = ENOC.MENT_ALUMNO.CARRERA_ID) " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentorPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListAlumFac(Connection conn, String periodoId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO " +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = '"+periodoId+"'" +
					" AND STATUS = 'A' "+ 
					" AND CARRERA_ID " +
					"   IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'))"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListAlumTodos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<MentAlumno> getListAlumTodos(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentAlumno> lisMentAlumno		= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO " +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = '"+periodoId+"'" +
					" AND STATUS = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				lisMentAlumno.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListAlumTodos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumno;
	}
	
	public ArrayList<String> getListAconsejados(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<String> list	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodoId+"' " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				list.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListAconsejados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<MentAlumno> getListMentores(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<MentAlumno> list	= new ArrayList<MentAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);
				list.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoUtil|getListMentores|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public static HashMap<String,MentAlumno> getMapMentorAlumno(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,MentAlumno> mapMentor = new HashMap<String,MentAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO "+
					" FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodoId+"' AND STATUS = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);				
				llave = mentor.getCodigoPersonal();
				mapMentor.put(llave, mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|getMapMentorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMentor;
	}
	
	public static HashMap<String,String> getMapMentor(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,String> mapMentor = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT MENTOR_ID, CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO "
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND STATUS = 'A'";			
			rs = st.executeQuery(comando);
			while (rs.next()){								
				llave = rs.getString("CODIGO_PERSONAL");
				mapMentor.put(llave, rs.getString("MENTOR_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|getMapMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMentor;
	}
	
	public HashMap<String, String> getAlumPorMent(Connection conn, String periodo) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT MENTOR_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID ='"+periodo+"' GROUP BY MENTOR_ID, ENOC.FACULTAD(CARRERA_ID)";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MENTOR_ID")+rs.getString("FACULTAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|getAlumPorMent|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> getAlumPorMentL(Connection conn, String periodo) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT  MENTOR_ID, COUNT (DISTINCT(CODIGO_PERSONAL)) AS TOTAL  FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID IN (SELECT DISTINCT(MENTOR_ID) FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = '1415') AND PERIODO_ID = '"+periodo+"' GROUP BY MENTOR_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MENTOR_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|getAlumPorMent|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	// Cuenta el numero de aconsejados de un mentor en una fecha determinada
	public HashMap<String, String> getAlumPorMent(Connection conn, String periodoId, String fecha) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = " SELECT MENTOR_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"' "
					+ " AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL GROUP BY MENTOR_ID, ENOC.FACULTAD(CARRERA_ID)";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("MENTOR_ID")+rs.getString("FACULTAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|getAlumPorMent|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static HashMap<String,MentAlumno> mapMentorAlumnoVigente(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,MentAlumno> mapMentor = new HashMap<String,MentAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MentAlumno mentor = new MentAlumno();
				mentor.mapeaReg(rs);				
				llave = mentor.getCodigoPersonal();
				mapMentor.put(llave, mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|mapMentorAlumnoVigente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMentor;
	}
	
public static HashMap<String,String> mapMentorAlumno(Connection conn, String periodoId ) throws SQLException{
		
		HashMap<String,String> mapMentor = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		String mentor				= "";
		
		try{
			comando = "SELECT MENTOR_ID, CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID IN("+periodoId+") AND STATUS = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){

				mentor = rs.getString("MENTOR_ID");				
				llave = rs.getString("CODIGO_PERSONAL");
				mapMentor.put(llave, mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.MentAlumnoUtil|mapMentorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapMentor;
	}

}