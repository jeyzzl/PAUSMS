// Clase para la tabla de Acceso
package aca.notifica.spring;

public class NotiCovid{
	private String matricula;
	private String fecha;
	private String respuesta;	
	
	// Constructor
	public NotiCovid(){		
		matricula 	= "-";
		fecha 		= null;
		respuesta 	= "NO";		
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}