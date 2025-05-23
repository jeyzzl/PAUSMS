package aca.alerta.spring;

public class AlertaPeriodo{
	
	private String periodoId		= "";
	private String periodoNombre	= "";
	private String fechaIni			= "";
	private String fechaFin			= "";
	private String modalidades		= "";
	private String estado			= "";
	private String excepto			= "";
	
	public AlertaPeriodo(){
		
		periodoId		= "";
		periodoNombre	= "";
		fechaIni		= "";
		fechaFin		= "";
		modalidades		= "";
		estado			= "";
		excepto			= "-";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
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

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getExcepto() {
		return excepto;
	}

	public void setExcepto(String excepto) {
		this.excepto = excepto;
	}

}