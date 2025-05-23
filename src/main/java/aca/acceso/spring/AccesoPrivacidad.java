package aca.acceso.spring;

public class AccesoPrivacidad {	
	
	private String codigoPersonal;
	private String fecha;
	
	
	public AccesoPrivacidad(){
		codigoPersonal	= "";
		fecha			= "";
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
}