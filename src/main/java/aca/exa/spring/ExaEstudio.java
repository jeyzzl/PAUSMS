package  aca.exa.spring;

import java.sql.*;

public class ExaEstudio{
	private String estudioId;	
	private String matricula;
	private String estudios;
	private String institucion;	
	private String periodo;
	private String fechaAct;
	private String eliminado;
	private String idEstudio;
	
	public ExaEstudio(){
		estudioId 			= "";
		matricula			= "";
		estudios 			= "";
		institucion			= "";
		periodo	 			= "";
		fechaAct			= "";
		eliminado			= "";
		idEstudio	 		= "";
	
	}

	public String getEstudioId() {
		return estudioId;
	}

	public void setEstudioId(String estudioId) {
		this.estudioId = estudioId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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

	public String getIdEstudio() {
		return idEstudio;
	}

	public void setIdEstudio(String idEstudio) {
		this.idEstudio = idEstudio;
	}
}