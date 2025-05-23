package aca.bec.spring;


public class BecAcceso {
	private String codigoPersonal;
	private String idEjercicio;
	private String idCcosto;
	private String fecha;
	private String usuario;
	
	public BecAcceso(){
		codigoPersonal	= "";
		idEjercicio		= "";
		idCcosto		= "";
		fecha			= "";
		usuario			= "";
	}	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getIdCcosto() {
		return idCcosto;
	}

	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
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
	
}