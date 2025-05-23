package aca.rotaciones.spring;

public class RotEspecialidad {

	private String especialidadId;
	private String especialidadNombre;
	private String cursoId;
	private String semanas;
	private String planId;
	
	public RotEspecialidad(){		
		especialidadId		= "0";
		especialidadNombre	= "-";
		cursoId			 	= "-";
		semanas				= "0";
		planId				= "-";
	}

	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getEspecialidadNombre() {
		return especialidadNombre;
	}

	public void setEspecialidadNombre(String especialidadNombre) {
		this.especialidadNombre = especialidadNombre;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getSemanas() {
		return semanas;
	}

	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
}
