// Clase para la tabla de Modulo
package aca.acceso.spring;

import java.util.Date;

public class AccesoClave{
	
	private String codigoPersonal;
	private String fecha;
	private String clave;
	private String ip;
	private int folio;
	private String fechaRecupera;
	
	// Constructor
	public AccesoClave(){
		codigoPersonal	= "";
		fecha			= "";
		clave			= "";
		ip				= "";
		folio			= 0;
		fechaRecupera	= "";
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
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

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFechaRecupera() {
		return fechaRecupera;
	}

	public void setFechaRecupera(String fechaRecupera) {
		this.fechaRecupera = fechaRecupera;
	}
}