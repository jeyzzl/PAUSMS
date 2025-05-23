package aca.covid.spring;

public class CovidPeriodo {
	
	private String periodoId;
	private String periodoNombre;
	private String fechaInicio;
	private String fechaFinal;
	
	public CovidPeriodo() {
		periodoId 		= "";
		periodoNombre 	= "";
		fechaInicio 	= "";
		fechaFinal 		= "";
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

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
}
