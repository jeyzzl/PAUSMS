// Bean del Catálogo de Documentos de Admision
package  aca.admision.spring;

public class AdmDocumento{
	private String documentoId;
	private String documentoNombre;
	private String tipo;
	private String comentario;
	private String original;
	private String orden;
	private String formatoId;
	private String corto;
	
	public AdmDocumento(){
		documentoId 		= "0";
		documentoNombre		= "-";
		tipo 				= "0";
		comentario			= "-";
		original			= "S";
		orden				= "0";
		formatoId			= "-";
		corto				= "-";
	}

	public String getCorto() {
		return corto;
	}

	public void setCorto(String corto) {
		this.corto = corto;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getFormatoId() {
		return formatoId;
	}

	public void setFormatoId(String formatoId) {
		this.formatoId = formatoId;
	}	
}