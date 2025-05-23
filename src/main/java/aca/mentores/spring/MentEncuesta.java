package aca.mentores.spring;

public class MentEncuesta {

	private String encuestaId;
	private String nombre;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	public MentEncuesta(){
		encuestaId 	= "";
		nombre		= "";
		fechaIni	= "";
		fechaFin	= "";
		estado		= "";
	}

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}