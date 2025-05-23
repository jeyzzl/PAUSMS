package aca.vigilancia.spring;

public class VigDoc{
	 
	private String documentoId;
	private String documentoNombre;	
	private String corto;
	
	public VigDoc(){
		documentoId			= "0";
		documentoNombre		= "-";
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
	public void setDocumentoNombre(String documentoNombre){
		this.documentoNombre = documentoNombre;
	}

	public String getCorto() {
		return corto;
	}
	public void setCorto(String corto) {
		this.corto = corto;
	}
	
}