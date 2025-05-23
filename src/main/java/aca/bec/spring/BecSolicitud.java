package aca.bec.spring;


public class BecSolicitud {
	private String codigoPersonal;
	private String folio;
	private String porCoordinador;
	private String coordinador;
	private String porComision;
	private String usuario;
	private String comentario;
	private String fecha;
	private String comCoordinador;
	private String periodoId		= "";
	
	public BecSolicitud(){
		codigoPersonal	= "";
		folio			= "";
		porCoordinador	= "";
		coordinador		= "";
		porComision		= "";
		usuario			= "";
		comentario		= "";
		fecha			= "";
		comCoordinador	= "";
		periodoId		= "";
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

	public String getPorCoordinador() {
		return porCoordinador;
	}

	public void setPorCoordinador(String porCoordinador) {
		this.porCoordinador = porCoordinador;
	}

	public String getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(String coordinador) {
		this.coordinador = coordinador;
	}

	public String getPorComision() {
		return porComision;
	}

	public void setPorComision(String porComision) {
		this.porComision = porComision;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComCoordinador() {
		return comCoordinador;
	}

	public void setComCoordinador(String comCoordinador) {
		this.comCoordinador = comCoordinador;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}	
	
}