package aca.trabajo.spring;

public class TrabPeriodo {
	
	private String periodoId;
	private String nombrePeriodo;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	
	public TrabPeriodo() {
		periodoId 		= "";
		nombrePeriodo 	= "";
		fechaIni 		= "";
		fechaFin		= "";
		estado 			= "";
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
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
