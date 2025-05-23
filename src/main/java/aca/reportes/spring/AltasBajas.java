package aca.reportes.spring;

public class AltasBajas {
	private String planId;
	private String carreraId;
	private String facultadId;
	private String matricula;
	private String nombre;

	
	public AltasBajas(){
		planId 			= "0";
		carreraId 		= "1";
		facultadId 		= "-";
		matricula 		= "-";
		nombre 			= "-";
	}


	public String getPlanId() {
		return planId;
	}


	public void setPlanId(String planId) {
		this.planId = planId;
	}


	public String getCarreraId() {
		return carreraId;
	}


	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}


	public String getFacultadId() {
		return facultadId;
	}


	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}