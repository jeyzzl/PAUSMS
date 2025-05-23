// Bean de documentos de admision del alumno
package  aca.admision.spring;

public class AdmDocAlum{
	private String folio;
	private String documentoId;
	private String estado;
	private String usuario;
	private String comentario;
	private String carta;
	
	public AdmDocAlum(){
		folio 			= "0";
		documentoId 	= "0";
		estado			= "-";
		usuario			= "-";
		comentario		= "";
		carta			= "S";
	}	
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}
}