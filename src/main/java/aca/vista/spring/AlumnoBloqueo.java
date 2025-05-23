package aca.vista.spring;

public class AlumnoBloqueo {
	private String codigoPersonal;
	private String cargaId;
	private String modalidadId;
	private String facultadId;
	private String carreraId;	
		
	public AlumnoBloqueo(){
		codigoPersonal	= "";
		cargaId	= "";
		modalidadId	= "";
		facultadId			= "";
		carreraId			= "";		
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

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

}