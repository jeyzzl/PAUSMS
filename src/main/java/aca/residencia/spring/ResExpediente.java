package aca.residencia.spring;

public class ResExpediente {
	private String folio;
	private String descripcion;
	private String estado;
	private String fecha;
	private String planId;
	private String codigoPersonal;
	private String comentario;
	
	public ResExpediente(){
		folio 			= "0";
		descripcion 	= "";
		estado 			= "0";
		planId 			= "0";
		fecha 			= "-";
		codigoPersonal 	= "0";
		comentario		= "-";
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
}