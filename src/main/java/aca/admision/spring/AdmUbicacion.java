package aca.admision.spring;

public class AdmUbicacion{
	private String folio;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String calle;
	private String numero;
	private String codigoPostal;	
	private String telefono;
	private String colonia;
		
	public AdmUbicacion(){
		folio 			= "";
		paisId 			= "";
		estadoId 		= "";
		ciudadId		= "";
		calle 			= "";
		numero 			= "";
		codigoPostal 	= "";		
		telefono		= "";
		colonia			= "";
	}
	
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getPaisId() {
		return paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}
	public String getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}
	public String getCiudadId() {
		return ciudadId;
	}
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}	

}