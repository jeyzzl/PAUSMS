package aca.residencia.spring;

public class ResCandado {

	private String codigoPersonal;
	private String comentario;
	private String usuario;
	private String fecha;
	private String estado;
	
	public ResCandado(){		
		estado			= "";
		codigoPersonal	= "";
		fecha			= "";
		comentario 		= "";
		usuario			= "";
	}
	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
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


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
}