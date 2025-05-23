// Clase para la vista ESTINTERNOS
package  aca.vista;

import java.sql.*;

public class EstInternos{
	private String facultadId;
	private String carreraId;
	private String codigoPersonal;
	private String dormitorio;	
	
	public EstInternos(){
		facultadId		= "";
		carreraId		= "";
		codigoPersonal	= "";
		dormitorio		= "";		
	}	
	
	/**
	 * @return Returns the carreraId.
	 */
	public String getCarreraId() {
		return carreraId;
	}
	/**
	 * @param carreraId The carreraId to set.
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	/**
	 * @return Returns the facultadId.
	 */
	public String getFacultadId() {
		return facultadId;
	}
	/**
	 * @param facultadId The facultadId to set.
	 */
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}
	/**
	 * @return Returns the dormitorio.
	 */
	public String getDormitorio() {
		return dormitorio;
	}
	/**
	 * @param dormitorio The dormitorio to set.
	 */
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		facultadId 		= rs.getString("FACULTAD_ID");
		carreraId		= rs.getString("CARRERA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		dormitorio		= rs.getString("DORMITORIO");
	}
	
	public void mapeaRegId( Connection conn, String carreraId ) throws SQLException{
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
				"FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, DORMITORIO "+			
				"FROM ENOC.ESTINTERNOS "+
				"WHERE CARRERA_ID = ? ");
			ps.setString(1, carreraId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
 		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInternos|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}	
	
}