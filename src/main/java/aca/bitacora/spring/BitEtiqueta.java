package aca.bitacora.spring;

public class BitEtiqueta {

	private String folio;
	private String etiquetaId;
	private String areaId;
	private String comentario;
	private String fecha;
	private String usuario;
	private String turnado;
	
	public BitEtiqueta(){
		folio 		= "";
		etiquetaId 	= "";
		areaId		= "";
		comentario 	= "";
		fecha 		= "";
		usuario 	= "";
		turnado		= "-";
	}	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEtiquetaId() {
		return etiquetaId;
	}

	public void setEtiquetaId(String etiquetaId) {
		this.etiquetaId = etiquetaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
	
	public String getTurnado() {
		return turnado;
	}

	public void setTurnado(String turnado) {
		this.turnado = turnado;
	}
	
}
