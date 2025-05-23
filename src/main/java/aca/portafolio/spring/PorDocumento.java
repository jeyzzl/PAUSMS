package aca.portafolio.spring;

public class PorDocumento {

	private String documentoId;
	private String documentoNombre;
	private String usuario;
	private String archivo;
	
	public PorDocumento(){
		documentoId 	= "";
		documentoNombre		= "";
		usuario	= "";		
		archivo		= "";
	}	
	
	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getDocumentoNombre() {
		return documentoNombre;
	}

	public void setDocumentoNombre(String documentoNombre) {
		this.documentoNombre = documentoNombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

}