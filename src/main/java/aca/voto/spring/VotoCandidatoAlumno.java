package aca.voto.spring;

public class VotoCandidatoAlumno {
	private String eventoId;	
	private String candidatoId;
	private String codigoPersonal;
	private String facultadId;
	private String orden;
	
	public VotoCandidatoAlumno(){
		eventoId		= "0";	
		candidatoId		= "0";
		codigoPersonal	= "0";
		facultadId		= "000";		
		orden			= "0";		
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCandidatoId() {
		return candidatoId;
	}

	public void setCandidatoId(String candidatoId) {
		this.candidatoId = candidatoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}