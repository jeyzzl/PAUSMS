package aca.bitacora.spring;

public class BitSolicitud {

	private String codigoPersonal;
	private String folio;
	private String tramiteId;
	private String status;
	private String textoAlumno;
	private String textoAdmin;
	private String fecha;
	private String folioTramite;
	
	public BitSolicitud(){
		codigoPersonal 	= "0";
		folio			= "0";
		tramiteId 		= "0";
		status 			= "-";
		textoAlumno 	= "-";
		textoAdmin		= "-";
		fecha			= "";
		folioTramite	= "0";
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

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTextoAlumno() {
		return textoAlumno;
	}

	public void setTextoAlumno(String textoAlumno) {
		this.textoAlumno = textoAlumno;
	}

	public String getTextoAdmin() {
		return textoAdmin;
	}

	public void setTextoAdmin(String textoAdmin) {
		this.textoAdmin = textoAdmin;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFolioTramite() {
		return folioTramite;
	}

	public void setFolioTramite(String folioTramite) {
		this.folioTramite = folioTramite;
	}
}
