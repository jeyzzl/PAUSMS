//Bean del periodo de vacaciones del alumno
package aca.alumno.spring;

public class AlumVacacion {
	private String nivelId;
	private String modalidadId;
	private String fExamen;
	private String fInicio;
	private String fFinal;
	
	public AlumVacacion(){
		nivelId		= "";
		modalidadId = "";
		fExamen		= "";
		fInicio		= "";
		fFinal		= "";		
	}
	
	public String getNivelId() {
		return nivelId;
	}


	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}


	public String getModalidadId() {
		return modalidadId;
	}


	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}


	public String getfExamen() {
		return fExamen;
	}


	public void setfExamen(String fExamen) {
		this.fExamen = fExamen;
	}


	public String getfInicio() {
		return fInicio;
	}


	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}


	public String getfFinal() {
		return fFinal;
	}


	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}
}