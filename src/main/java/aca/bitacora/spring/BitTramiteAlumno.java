package aca.bitacora.spring;

public class  BitTramiteAlumno {
	
	private String folio;
	private String codigoPersonal;
	private String tramiteId;
	private String fechaInicio;
	private String fechaFinal;
	private String estado;
	private String areaId;
	private String areaOrigen;
	private String folioOrigen;
	private String usuario;
	private String comentario;
	
	public BitTramiteAlumno(){
		folio 				= "0";
		codigoPersonal		= "0";
		tramiteId			= "0";
		fechaInicio			= aca.util.Fecha.getHoy();
		fechaFinal			= "01/01/2000";
		estado				= "0";
		areaId				= "0";
		areaOrigen			= "0";
		folioOrigen			= "-";
		usuario 			= "-";
		comentario 			= "-";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaOrigen() {
		return areaOrigen;
	}

	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
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
