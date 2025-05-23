package aca.voto.spring;

public class VotoAlumno {
	private String codigoPersonal;
	private String eventoId;
	private String candidatoId;
	private String voto;
	private String fecha;
	
	public VotoAlumno(){
		codigoPersonal	= "";
		eventoId		= "0";
		candidatoId		= "0";
		voto			= "";
		fecha			= aca.util.Fecha.getHoy();
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCandidatoId() {
		return candidatoId;
	}

	public void setCandidatoId(String candidatoId) {
		this.candidatoId = candidatoId;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
}