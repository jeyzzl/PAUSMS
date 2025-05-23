package aca.financiero.spring;

public class FinPeriodo {
	
	private String periodoId;
	private String fechaIni;
	private String fechaFin;
	private String cargas;
	private String modalidades;
	private String mensaje;
	private String estado;
	private String cantidad;
	
	public FinPeriodo(){
		periodoId	= "0";
		fechaIni	= aca.util.Fecha.getHoy();
		fechaFin	= aca.util.Fecha.getHoy();
		cargas		= "-";
		modalidades	= "-";
		mensaje		= "-";
		estado		= "A";
		cantidad	= "0";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
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

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
}