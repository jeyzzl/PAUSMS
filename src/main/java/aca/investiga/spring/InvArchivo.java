package aca.investiga.spring;

public class InvArchivo {
	private String proyectoId;
	private String folio;
	private String nombre;
	private byte[] archivo;
	
	public InvArchivo(){
		proyectoId		= "";
		folio 			= "0";
		nombre			= "-";
		archivo			= null;
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}	
}
