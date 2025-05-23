package aca.vigilancia.spring;

public class VigDocumento{
	 
	private String autoId;
	private String documentoId;
	private String hoja;
	private byte[] archivo;
	private String nombre;
	
	public VigDocumento(){
		autoId				= "0";
		documentoId			= "0";
		hoja 				= "1";
		archivo				= null;
		nombre				= "-";
	}

	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getDocumentoId() {
		return documentoId;
	}
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHoja() {
		return hoja;
	}
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}	
}