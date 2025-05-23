package aca.vigilancia.spring;

public class VigDocAuto{
	 
	private String autoId;
	private String documentoId;
	private String vigencia;	
	private String comentario;
	
	public VigDocAuto(){
		autoId				= "0";
		documentoId			= "0";
		vigencia 			= aca.util.Fecha.getHoy();		
		comentario			= "-";
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

	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
}