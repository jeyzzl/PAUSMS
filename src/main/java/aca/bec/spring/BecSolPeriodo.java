package aca.bec.spring;

public class BecSolPeriodo{
	
	private String periodoId		= "";
	private String periodoNombre	= "";
	private String fecha			= "";
	
	public BecSolPeriodo(){
		periodoId		= "";
		periodoNombre	= "";
		fecha		= "";
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}