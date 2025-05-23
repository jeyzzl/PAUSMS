// Bean de Estado del alumno en el procesos de matrxcula( Por cada bloque).
package  aca.objeto;

import java.sql.*;

public class Reprobado{
	private String cargaId;
	private String codigoPersonal;	
	private String carreraId;
	private String numMat;	
	
	public Reprobado(){
		cargaId			= "";
		codigoPersonal	= "";
		carreraId		= "";
		numMat			= "0";		
	}	
	
	
	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}


	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
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
	 * @return the numMat
	 */
	public String getNumMat() {
		return numMat;
	}


	/**
	 * @param numMat the numMat to set
	 */
	public void setNumMat(String numMat) {
		this.numMat = numMat;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId 		= rs.getString("CARGA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");		
		carreraId		= rs.getString("CARRERA_ID");
		numMat	 		= rs.getString("NUMMAT");		
	}	
}