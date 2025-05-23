package aca.mentores.spring;

public class MentAlumnoIngresos {
	private String codigoPersonal;
	private String cargaId;
	private String propios;
	private String semestre;
	private String colportaje;
	private String beca;
	
	public MentAlumnoIngresos(){
		codigoPersonal		= "";
		cargaId				= "";
		propios				= "";
		semestre			= "";
		colportaje			= "";
		beca				= "";
	}

	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCargaId() {
		return cargaId;
	}


	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}


	public String getPropios() {
		return propios;
	}


	public void setPropios(String propios) {
		this.propios = propios;
	}


	public String getSemestre() {
		return semestre;
	}


	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}


	public String getColportaje() {
		return colportaje;
	}


	public void setColportaje(String colportaje) {
		this.colportaje = colportaje;
	}


	public String getBeca() {
		return beca;
	}


	public void setBeca(String beca) {
		this.beca = beca;
	}

}