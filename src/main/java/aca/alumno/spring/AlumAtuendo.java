package aca.alumno.spring;

public class AlumAtuendo {
	
	private String atuendoId;
	private String descripcion;
	private String precio;
	
	public AlumAtuendo() {
		atuendoId 	= "";
		descripcion = "";
		precio 		= "";
	}

	public String getAtuendoId() {
		return atuendoId;
	}

	public void setAtuendoId(String atuendoId) {
		this.atuendoId = atuendoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
}
