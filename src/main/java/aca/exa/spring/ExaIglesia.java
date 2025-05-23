package  aca.exa.spring;

import java.sql.*;

public class ExaIglesia{
	private String iglesiaId;	
	private String matricula;
	private String iglesia;
	private String funcion;	
	private String fechaAct;
	private String eliminado;
	private String idEgresadoIglesia;
	
	public ExaIglesia(){
		iglesiaId 			= "";
		matricula			= "";
		iglesia 			= "";
		funcion				= "";
		fechaAct			= "";
		eliminado			= "";
		idEgresadoIglesia	= "";
	}

	public String getIglesiaId() {
		return iglesiaId;
	}

	public void setIglesiaId(String iglesiaId) {
		this.iglesiaId = iglesiaId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIglesia() {
		return iglesia;
	}

	public void setIglesia(String iglesia) {
		this.iglesia = iglesia;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEgresadoIglesia() {
		return idEgresadoIglesia;
	}

	public void setIdEgresadoIglesia(String idEgresadoIglesia) {
		this.idEgresadoIglesia = idEgresadoIglesia;
	}
	
	
	
}