/**
 * 
 */
package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author etorres
 *
 */
public class HcaMaestroActividad {
	private String codigoPersonal;
	private String cargaId;
	private String actividadId;
	private String semanas;
	private String horas;
	
	public HcaMaestroActividad(){
		codigoPersonal	= "0";
		cargaId			= "0";
		actividadId		= "0";
		semanas			= "16";
		horas			= "1";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getActividadId() {
		return actividadId;
	}
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getSemanas() {
		return semanas;
	}
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}	
}