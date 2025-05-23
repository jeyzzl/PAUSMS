package aca.federacion.spring;

public class FedCandidato {
	
	private String eventoId;
	private String candidatoId;
	private String candidatoNombre;
	private String candidatos;
	private String orden;
	
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

}
