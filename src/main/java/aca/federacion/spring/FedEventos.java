package aca.federacion.spring;

public class FedEventos{	
	private String eventoId;
	private String eventoNombre;
	private String eventoDescripcion;
	private String fechaIni;
	private String fechaFin;
	private String tipo;
	
	public FedEventos(){
		eventoId			= "";
		eventoNombre		= "";
		fechaIni			= "";
		fechaFin			= "";
		eventoDescripcion	= "";
		tipo				= "";
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

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public String getEventoDescripcion() {
		return eventoDescripcion;
	}

	public void setEventoDescripcion(String eventoDescripcion) {
		this.eventoDescripcion = eventoDescripcion;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
}