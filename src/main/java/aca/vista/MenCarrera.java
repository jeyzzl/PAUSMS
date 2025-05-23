package aca.vista;
import java.sql.*;

public class MenCarrera {
	private String periodoId;	
	private String facultadId;
	private String facultadNombre;
	private String carreraId;
	private String carreraNombre;
	private String mentorId;
	private String mentorNombre;
	
	public MenCarrera(){
		periodoId		= "";	
		facultadId		= "";
		facultadNombre	= "";
		carreraId		= "";
		carreraNombre	= "";
		mentorId		= "";
		mentorNombre	= "";
	}
	
		
	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}


	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}


	/**
	 * @return the carreraNombre
	 */
	public String getCarreraNombre() {
		return carreraNombre;
	}


	/**
	 * @param carreraNombre the carreraNombre to set
	 */
	public void setCarreraNombre(String carreraNombre) {
		this.carreraNombre = carreraNombre;
	}


	/**
	 * @return the facultadId
	 */
	public String getFacultadId() {
		return facultadId;
	}


	/**
	 * @param facultadId the facultadId to set
	 */
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}


	/**
	 * @return the facultadNombre
	 */
	public String getFacultadNombre() {
		return facultadNombre;
	}


	/**
	 * @param facultadNombre the facultadNombre to set
	 */
	public void setFacultadNombre(String facultadNombre) {
		this.facultadNombre = facultadNombre;
	}


	/**
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}


	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}


	/**
	 * @return the mentorNombre
	 */
	public String getMentorNombre() {
		return mentorNombre;
	}


	/**
	 * @param mentorNombre the mentorNombre to set
	 */
	public void setMentorNombre(String mentorNombre) {
		this.mentorNombre = mentorNombre;
	}

	

	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}


	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId			= rs.getString("PERIODO_ID");
		facultadId 			= rs.getString("FACULTAD_ID");
		facultadNombre		= rs.getString("FACULTAD_NOMBRE");
		carreraId			= rs.getString("CARRERA_ID");
		carreraNombre 		= rs.getString("CARRERA_NOMBRE");
		mentorId			= rs.getString("MENTOR_ID");
		mentorNombre		= rs.getString("MENTOR_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String mentorId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT"+
					" PERIODO_ID, FACULTADO_ID, FACULTAD_NOMBRE, CARRERA_ID," +
					" CARRERA_NOMBRE, MENTOR_ID, MENTOR_NOMBRE FROM ENOC.MEN_CARRERA"+
					" WHERE MENTOR_ID = ? ");
			ps.setString(1, mentorId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenCarrera|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public boolean existeReg(Connection conn, String periodoId, String carreraId, String mentorId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MEN_CARRERA "+
				"WHERE PERIODO_ID = ? " +
				"AND CARRERA_ID = ? " +
				"AND MENTOR_ID = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, carreraId);
			ps.setString(3, mentorId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CarreraMentor|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}