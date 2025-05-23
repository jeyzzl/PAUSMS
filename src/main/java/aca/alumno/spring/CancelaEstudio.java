package aca.alumno.spring;

public class CancelaEstudio {
	private String codigoPersonal;
	private String planId;
	private String usuario;
	private String fecha;
	private String comentario;
	private String estado;
	
	public CancelaEstudio(){
		codigoPersonal	= "0";
		planId			= "0";
		usuario			= "-";
		fecha			= "";
		comentario		= "-";
		estado			= "-";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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