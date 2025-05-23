package aca.voto.spring;

public class VotoCandidato {
	private String eventoId;	
	private String candidatoId;
	private String candidatoNombre;
	private String candidatos;
	private String candidatas;
	private String facultades;
	private String estado;
	private String ganador;
	private String orden;
	
	public VotoCandidato(){
		eventoId		= "0";	
		candidatoId		= "0";
		candidatoNombre	= "-";
		candidatos		= "";
		candidatas		= "-";
		facultades		= "-";
		estado			= "A";
		ganador			= "0";
		orden			= "";
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

	public String getFacultades() {
		return facultades;
	}

	public void setFacultades(String facultades) {
		this.facultades = facultades;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCandidatoId(String candidatoId) {
		this.candidatoId = candidatoId;
	}

	public String getCandidatoNombre() {
		return candidatoNombre;
	}

	public void setCandidatoNombre(String candidatoNombre) {
		this.candidatoNombre = candidatoNombre;
	}

	public String getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(String candidatos) {
		this.candidatos = candidatos;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getCandidatas() {
		return candidatas;
	}

	public void setCandidatas(String candidatas) {
		this.candidatas = candidatas;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	
}