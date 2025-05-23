package aca.financiero.spring;

public class FinBloqueo {
	
	private String periodoId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	private String usuario;
	
	public FinBloqueo(){
		periodoId		= "0";
		codigoPersonal	= "0";
		fecha			= aca.util.Fecha.getHoy();
		usuario 		= "";	
		estado			= "A";	
	}

	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
}