package aca.admision.spring;

public class AdmPasos {
	private String folio;
	private String fecha;
	private String usuario;
	private String pasos;
	private String estado;
	private String comentario;
	
	public AdmPasos(){
		folio 		= "0";
		fecha 		= aca.util.Fecha.getHoy();
		usuario 	= "0";
		pasos		= "0";
		estado		= "0";
		comentario 	= "-";
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}