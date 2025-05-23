package aca.admision.spring;

public class AdmComentario {

	private String folio;
	private String comentarioId;
	private String tipo;
	private String usuario;
	private String fecha;
	private String comentario;
	private String estado;
	
	public AdmComentario() {
		folio 			= "";
		comentarioId 	= "0";
		tipo 			= "";
		usuario 		= "";
		fecha 			= "";
		comentario 		= "";
		estado 			= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getComentarioId() {
		return comentarioId;
	}

	public void setComentarioId(String comentarioId) {
		this.comentarioId = comentarioId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
