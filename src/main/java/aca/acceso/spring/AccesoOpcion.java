package aca.acceso.spring;

public class AccesoOpcion{
	
	private String codigoPersonal;
	private String opcionId;
	private String fecha;
	private String usuario;
	
	public AccesoOpcion(){
		codigoPersonal	= "";
		opcionId		= "";
		fecha			= null;
		usuario 		= "9800308";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getOpcionId() {
		return opcionId;
	}
	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
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