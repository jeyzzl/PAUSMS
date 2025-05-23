package aca.matricula.spring;

public class MatAlumno {
	private String eventoId;
	private String codigoPersonal;
	private String planId;
	private String fecha;	
	private String usuario;
	private String modo;
	private String estado;
	
	public MatAlumno(){
		eventoId		= "0";
		codigoPersonal	= "0";
		planId			= "0";		
		fecha			= aca.util.Fecha.getHoy();		
		usuario			= "0";	
		usuario			= "V";	
		usuario			= "P";	
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}