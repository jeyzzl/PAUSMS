package aca.matricula.spring;

public class MatEvento {
	private String eventoId;
	private String cargaId;
	private String eventoNombre;
	private String estado;
	
	public MatEvento(){
		eventoId		= "0";
		cargaId 		= "0";
		eventoNombre	= "";
		estado 	= "A";
	}

	public String getEventoId() {
		return eventoId;
	}
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoEstado) {
		this.eventoNombre = eventoEstado;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	
	
}