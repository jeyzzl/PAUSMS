package aca.admision.spring;

public class AdmEvaluacionNota {
	
	private String evaluacionId;
	private String folio;
	private String nota;
	private String fecha;
	private String usuario;
	private String comentario;

	public AdmEvaluacionNota() {
		evaluacionId 	= "";
		folio 			= "";
		nota 			= "0";
		fecha 			= "";
		usuario 		= "-";
		comentario 		= "-";
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	
}
