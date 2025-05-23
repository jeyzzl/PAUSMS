//Beans de la tabla ARCH_DOCUMENTOS

package aca.archivo.spring;

public class ArchDocumentos {
	private String idDocumento;
	private String descripcion;
	private String imagen;
	private String orden;
	private String mostrar;

	public ArchDocumentos(){
		idDocumento		= "";
		descripcion		= "";
		imagen			= "";
		orden			= "";
		mostrar			= "";
	}
	
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}
}		