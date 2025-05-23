package aca.reportes.spring;

import java.sql.SQLException;
import java.sql.*;

public class MateriasInsc {
	private String Matricula;
	private String Nombre;
	private String ApellidoPaterno;
	private String ApellidoMaterno;
	private String Modalidad;
	private String CFin;
	private String Plan;
	private String NumMaterias;
	private String Creditos;
	private String NombreMateria;
	private String ClaveMateria;

	public MateriasInsc(){
		Matricula			= "";
		Nombre				= "";
		ApellidoPaterno		= "";
		ApellidoMaterno		= "";
		Modalidad			= "";
		CFin				= "";
		Plan				= "";
		NumMaterias			= "";
		Creditos			= "";
		NombreMateria		= "";
		ClaveMateria		= "";
	}

	public String getApellidoMaterno() {
		return ApellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return ApellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = apellidoPaterno;
	}

	public String getCFin() {
		return CFin;
	}

	public void setCFin(String fin) {
		CFin = fin;
	}

	public String getClaveMateria() {
		return ClaveMateria;
	}

	public void setClaveMateria(String claveMateria) {
		ClaveMateria = claveMateria;
	}

	public String getCreditos() {
		return Creditos;
	}

	public void setCreditos(String creditos) {
		Creditos = creditos;
	}

	public String getMatricula() {
		return Matricula;
	}

	public void setMatricula(String matricula) {
		Matricula = matricula;
	}

	public String getModalidad() {
		return Modalidad;
	}

	public void setModalidad(String modalidad) {
		Modalidad = modalidad;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getNombreMateria() {
		return NombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		NombreMateria = nombreMateria;
	}

	public String getNumMaterias() {
		return NumMaterias;
	}

	public void setNumMaterias(String numMaterias) {
		NumMaterias = numMaterias;
	}

	public String getPlan() {
		return Plan;
	}

	public void setPlan(String plan) {
		Plan = plan;
	}	

	public void mapeaReg(ResultSet rs ) throws SQLException{
		Matricula 			= rs.getString("Matricula");
		Nombre 				= rs.getString("Nombre");
		ApellidoPaterno		= rs.getString("ApellidoPaterno");
		ApellidoMaterno 	= rs.getString("ApellidoMaterno");
		Modalidad		 	= rs.getString("Modalidad");
		CFin				= rs.getString("CFin");
		Plan				= rs.getString("Plan");
		NumMaterias			= rs.getString("NumMaterias");
		Creditos			= rs.getString("Creditos");
	}
}
	