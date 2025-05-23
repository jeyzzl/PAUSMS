package aca.edo.spring;

public class EdoPeriodo {
	private String periodoId;
	private String periodoNombre;
	private String fInicio;
	private String fFinal;
	private String estado;
	
	public EdoPeriodo(){
		periodoId		= "";
		periodoNombre	= "";
		fInicio			= "";
		fFinal			= "";
		estado 			= "";
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

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}