package aca.catalogo.spring;

public class CatAsignacion {
	private String asignacionId;
	private String asignacionNombre;
	private String direccion;
	private String telefono;
	
	public CatAsignacion(){
		asignacionId 		= "0";
		asignacionNombre	= "-";
		direccion			= "-";
		telefono			= "-";
	}

	public String getAsignacionNombre() {
		return asignacionNombre;
	}

	public void setAsignacionNombre(String asignacionNombre) {
		this.asignacionNombre = asignacionNombre;
	}

	public String getAsignacionId() {
		return asignacionId;
	}

	public void setAsignacionId(String asignacionId) {
		this.asignacionId = asignacionId;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}