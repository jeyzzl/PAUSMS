package aca.investiga.spring;

public class InvComentario {
	
	private String proyectoId;
	private String folio;
	private String fecha;
	private String codigoPersonal;
	private String tipo;
	private String comentario;

	public InvComentario(){
		proyectoId 		= "";
		folio			= "";
		fecha			= "";
		codigoPersonal	= "";
		tipo			= "";
		comentario		= "";
	}
	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	
}