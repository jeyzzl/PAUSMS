package aca.tit.spring;

public class TitDocumentos {
	
	private String documentoId;
	private String documentoNombre;	
	private String orden;	
	private String tipo;
	
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
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}