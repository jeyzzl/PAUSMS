/**
 * 
 */
package aca.hca;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class HcaMaestroActividad {
	private String codigoPersonal;
	private String cargaId;
	private String actividadId;
	private String semanas;
	private String horas;
	
	public HcaMaestroActividad(){
		codigoPersonal	= "";
		cargaId			= "";
		actividadId		= "";
		semanas			= "16";
		horas			= "1";
	}

	/**
	 * @return the actividadId
	 */
	public String getActividadId() {
		return actividadId;
	}

	/**
	 * @param actividadId the actividadId to set
	 */
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
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
	 * @return the semanas
	 */
	public String getSemanas() {
		return semanas;
	}

	/**
	 * @param semanas the semanas to set
	 */
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}
	
	/**
	 * @return the horas
	 */
	public String getHoras() {
		return horas;
	}

	/**
	 * @param horas the horas to set
	 */
	public void setHoras(String horas) {
		this.horas = horas;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cargaId			= rs.getString("CARGA_ID");
		actividadId		= rs.getString("ACTIVIDAD_ID");
		semanas			= rs.getString("SEMANAS");
		horas			= rs.getString("HORAS");
	}
}