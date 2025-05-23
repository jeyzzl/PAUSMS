package aca.edo.spring;

public class Edo {
	private String edoId;
	private String nombre;
	private String fInicio;
	private String fFinal;
	private String periodoId;
	private String tipo;
	private String modalidad;
	private String encabezado;
	private String tipoEncuesta;
	private String cargas;
	private String excepto;
	
	public Edo(){
		edoId			= "0";
		nombre			= "-";
		fInicio			= "";
		fFinal			= "";
		periodoId 		= "0";
		tipo			= "-";
		modalidad		= "0";
		encabezado		= "-";
		tipoEncuesta	= "E";
		cargas			= "-";
		excepto			= "-";
	}

	public String getEdoId() {
		return edoId;
	}
	
	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getFFinal() {
		return fFinal;
	}

	public void setFFinal(String final1) {
		fFinal = final1;
	}	
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}
	
	public String getTipoEncuesta() {
		return tipoEncuesta;
	}

	public void setTipoEncuesta(String tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}
	
	public String getExcepto() {
		return excepto;
	}

	public void setExcepto(String excepto) {
		this.excepto = excepto;
	}
	
}