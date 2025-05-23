package aca.financiero.spring;

public class FinComentario {
	private String codigoPersonal;
	private String folio;
	private String comentario;
	private String fecha;
	private String usuario;	
	private String tipo;
	
	public FinComentario(){
		codigoPersonal	= "0000000";
		folio			= "0";
		comentario		= "-";
		fecha			= aca.util.Fecha.getHoy();
		usuario			= "0000000";
		tipo			= "X";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
}