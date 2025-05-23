package aca.kardex;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KrdxAlta {
	
	public String codigoPersonal;
	public String cursoCargaId;
	public String cargaId;
	public String bloqueId;
	public String creditos;
	public String cursoId;
	public String carreraId;
	public String modalidadId;
	public String year;
	public String tipo;
	
	public KrdxAlta() {
		codigoPersonal 	= "";
		cursoCargaId 	= "";
		cargaId 		= "";
		bloqueId 		= "";
		creditos 		= "";
		cursoId 		= "";
		carreraId 		= "";
		modalidadId 	= "";
		year 			= "";
		tipo 			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		cargaId 		= rs.getString("CARGA_ID");
		bloqueId 		= rs.getString("BLOQUE_ID");
		creditos 		= rs.getString("CREDITOS");
		cursoId 		= rs.getString("CURSO_ID");
		carreraId 		= rs.getString("CARRERA_ID");
		modalidadId 	= rs.getString("MODALIDAD_ID");
		year 			= rs.getString("YEAR");
		tipo 			= rs.getString("TIPO");	
	}

}