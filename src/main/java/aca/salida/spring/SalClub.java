package aca.salida.spring;

public class SalClub {
	private String grupoId;
	private String grupoNombre;
	private String responsable;
	private String alumnos;
	
	public SalClub() {
		grupoId     = "0";
		grupoNombre = "-";
		responsable = "";
		alumnos		= "";
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
	}

}
