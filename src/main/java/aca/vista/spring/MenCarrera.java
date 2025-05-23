package aca.vista.spring;

public class MenCarrera {
	private String periodoId;	
	private String facultadId;
	private String facultadNombre;
	private String carreraId;
	private String carreraNombre;
	private String mentorId;
	private String mentorNombre;
	
	public MenCarrera(){
		periodoId		= "";	
		facultadId		= "";
		facultadNombre	= "";
		carreraId		= "";
		carreraNombre	= "";
		mentorId		= "";
		mentorNombre	= "";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getFacultadNombre() {
		return facultadNombre;
	}

	public void setFacultadNombre(String facultadNombre) {
		this.facultadNombre = facultadNombre;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getCarreraNombre() {
		return carreraNombre;
	}

	public void setCarreraNombre(String carreraNombre) {
		this.carreraNombre = carreraNombre;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	public String getMentorNombre() {
		return mentorNombre;
	}

	public void setMentorNombre(String mentorNombre) {
		this.mentorNombre = mentorNombre;
	}
	
}