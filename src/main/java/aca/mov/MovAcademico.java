// Clase para la tabla de Acceso
package aca.mov;

public class MovAcademico{
	private int codigoPersonal;
	private String modalidad;
	private String inscrito;
	private String residencia;
	private String dormitorio;
	
	// Constructor
	public MovAcademico(){		
		codigoPersonal 	= 0;
		modalidad 		= "-";
		inscrito 	    = "-";
		residencia		= "-";;
		dormitorio		= "-";
	}

	public int getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(int codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getInscrito() {
		return inscrito;
	}

	public void setInscrito(String inscrito) {
		this.inscrito = inscrito;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public String getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}

}