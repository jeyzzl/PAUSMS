package aca.federacion.spring;

public class FedVotos {
	
	private String eventoId;
	private String candidatoId;
	private String codigoPersonal;
	private String fecha;
	private String voto;
	
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}
	
}
