package aca.conva.spring;

public class ConvHistorial {
	private String convalidacionId;
	private String folio;
	private String fecha;
	private String usuario;
	private String estado;
	
	public ConvHistorial(){
		convalidacionId	= "";
		folio			= "";
		fecha			= "";
		usuario			= "";
		estado			= "";
	}

	public String getConvalidacionId() {
		return convalidacionId;
	}

	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	

}