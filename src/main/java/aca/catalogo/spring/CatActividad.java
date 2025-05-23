package aca.catalogo.spring;


public class CatActividad {
	private String actividadId;	
	private String descripcion;
	
	public CatActividad(){
		actividadId     = "";
		descripcion     = "";
	}
	
	public String getActividadId() {
		return actividadId;
	}
	
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}