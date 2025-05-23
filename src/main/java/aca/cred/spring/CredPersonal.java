package aca.cred.spring;

public class CredPersonal {
	private String eventoId;
	private String codigoPersonal;
	private String nombre;
	
	public CredPersonal(){
		eventoId 		= "";
		codigoPersonal = "";
		nombre 		= "";
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
