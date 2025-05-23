package aca.admision.spring;

public class AdmAcademico {
	private String folio;
	private String modalidad;
	private String nivelId;
	private String carreraId;
	private String fecha;
	private String planId;
	private String periodoId;
	private String tipo;
		
	public AdmAcademico(){
		folio 			= "";
		modalidad 		= "";
		nivelId 		= "";
		carreraId		= "";
		fecha 			= "";
		planId			= "";
		periodoId 		= "";
		tipo 			= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}