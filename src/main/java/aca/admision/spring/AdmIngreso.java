package aca.admision.spring;

public class AdmIngreso {
	private String periodoId;
	private String periodoNombre;
	private String estado;
	private String fecha;

	
	public AdmIngreso(){
		periodoId 		= "0";
		periodoNombre	= "-";
		estado		 	= "A";
		fecha		 	= aca.util.Fecha.getHoy();
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
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado (String estado) {
		this.estado = estado;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}