package aca.exp.spring;

public class ExpDocumento {
	private String documentoId;
	private String documentoNombre;	
	private String orden;
	private String corto;

	public ExpDocumento(){
		documentoId			= "0";
		documentoNombre		= "-";
		orden				= "0";
		corto 				= "-";
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

	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getCorto() {
		return corto;
	}
	public void setCorto(String corto) {
		this.corto = corto;
	}	
}		