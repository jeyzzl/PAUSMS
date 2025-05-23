/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.internado.spring;

import java.util.Date;

/**
 * @author Ery   
 */
public class IntAlumno {
	private String dormitorioId;
	private String cuartoId;
	private String codigoPersonal;
	private String orden;
	private String estado;
	private Date fechaInicio;
	private Date fechaFinal;

	public IntAlumno (){
		dormitorioId		= "0";
		cuartoId			= "0";
		codigoPersonal		= "0";
		orden 				= "0";
		estado				= "A";
		fechaInicio			= null;
		fechaFinal			= null;
	}
	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getCuartoId() {
		return cuartoId;
	}
	public void setCuartoId(String cuartoId) {
		this.cuartoId = cuartoId;
	}
	
	public String getDormitorioId() {
		return dormitorioId;
	}
	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}		

	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	
}