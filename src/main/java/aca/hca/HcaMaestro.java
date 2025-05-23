/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class HcaMaestro {
	private String codigoPersonal;
	private String facultadId;
	private String carreraId;
	private String estado;
	
	public HcaMaestro(){
		codigoPersonal	= "";
		facultadId		= "";
		carreraId		= "";
		estado			= "";
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
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		facultadId		= rs.getString("FACULTAD_ID");
		carreraId		= rs.getString("CARRERA_ID");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
		ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
				" FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO" + 
				" WHERE CODIGO_PERSONAL = ?");
		
		ps.setString(1, codigoPersonal);
		
		rs = ps.executeQuery();
		
		if(rs.next()){
			
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	
	}
	
}