package aca.mensaje.spring;

public class Mensaje {
	 
	private String codigoPersonal;
	private String mensaje1;
	private String mensaje2;
 	
 	public Mensaje(){
 		codigoPersonal		= "";	
 		mensaje1			= "";	
 		mensaje2			= "";	
 	} 	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getMensaje1() {
		return mensaje1;
	}

	public void setMensaje1(String mensaje1) {
		this.mensaje1 = mensaje1;
	}

	public String getMensaje2() {
		return mensaje2;
	}

	public void setMensaje2(String mensaje2) {
		this.mensaje2 = mensaje2;
	}	
}
 	