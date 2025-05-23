// Clase para la vista ESTEXTERNOS
package  aca.vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstExternos{
	private String facultadId;
	private String carreraId;
	private String codigoPersonal;
	private String razon;
	private String fecha;
	
	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public EstExternos(){
		facultadId		= "";
		carreraId		= "";
		codigoPersonal	= "";
		razon			= "";
		fecha			= "";
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
	 * @return Returns the razon.
	 */
	public String getRazon() {
		return razon;
	}
	/**
	 * @param razon The razon to set.
	 */
	public void setRazon(String razon) {
		this.razon = razon;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		facultadId 		= rs.getString("FACULTAD_ID");
		carreraId		= rs.getString("CARRERA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		razon			= rs.getString("RAZON");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String carreraId ) throws SQLException{
		 
		PreparedStatement ps = null;
		ResultSet rs = null;
 		try{
			ps = conn.prepareStatement("SELECT "+
				"FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, RAZON "+			
				"FROM ENOC.ESTEXTERNOS "+
				"WHERE CARRERA_ID = ? ");
			ps.setString(1, carreraId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
 		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstExternos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
}