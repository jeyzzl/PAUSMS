package aca.ssoc.spring;

public class SsocInicio {
	private String codigoPersonal;
	private String planId;
	private String fecha;
	private String porcentaje;
	private String semestre;
	
	public SsocInicio(){
		codigoPersonal	= "";
		planId			= "";
		fecha			= "";
		porcentaje		= "";
		semestre		= "";
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the porcentaje
	 */
	public String getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	/**
	 * @return the semestre
	 */
	public String getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre the semestre to set
	 */
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
}