package  aca.exa.spring;

import java.sql.*;

public class ExaEvento{
	private String eventoId;	
	private String nombre;
	private String lugar;	
	private String fechaEvento;
	private String fechaActualizacion;
	private String eliminado;
	private String idEvento;
	
	public ExaEvento(){
		eventoId 			= "0";
		nombre 				= "";
		lugar				= "";
		fechaEvento	 		= aca.util.Fecha.getHoy();
		fechaActualizacion	= "";
		eliminado			= "0";
		idEvento			= "-";
	}

	public String getEventoId() {
		return eventoId;
	}
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(String fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaAct) {
		this.fechaActualizacion = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}
	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
}