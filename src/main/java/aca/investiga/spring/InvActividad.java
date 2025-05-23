package aca.investiga.spring;

public class InvActividad {
	
	private String proyectoId;
	private String actividadId;
	private String actividadNombre;
	private String fechaIni;
	private String fechaFin;
	
	public InvActividad() {
		proyectoId 		= "";
		actividadId 	= "-";
		actividadNombre = "-";
		fechaIni 		= "-";
		fechaFin 		= "-";
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getActividadId() {
		return actividadId;
	}
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}
	public String getActividadNombre() {
		return actividadNombre;
	}
	public void setActividadNombre(String actividadNombre) {
		this.actividadNombre = actividadNombre;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}	
	
}
