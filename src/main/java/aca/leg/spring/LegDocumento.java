package aca.leg.spring;

public class LegDocumento {
	public String idDocumentos;
	public String descripcion;
	public String imagen;
	
	public LegDocumento(){
		idDocumentos	= "0";
		descripcion		= "-";
		imagen			= "";
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

	public String getIdDocumentos() {
		return idDocumentos;
	}	

	public void setIdDocumentos(String idDocumentos) {
		this.idDocumentos = idDocumentos;
	}	

	public String getImagen() {
		return imagen;
	}	

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}