package aca.portafolio.spring;

public class PorEmpDocto {
	
	private String codigoPersonal;
	private String periodoId;
	private String documentoId;
	private String hojas;
	private String fecha;
	private String usuario;
	
	public PorEmpDocto(){		
		codigoPersonal 	= "";
		periodoId		= "";
		documentoId 	= "";
		hojas			= "";
		fecha			= "";
		usuario 		= ""; 
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getHojas() {
		return hojas;
	}

	public void setHojas(String hojas) {
		this.hojas = hojas;
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

}