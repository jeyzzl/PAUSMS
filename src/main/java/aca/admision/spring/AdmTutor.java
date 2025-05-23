package aca.admision.spring;

public class AdmTutor {
	private String folio;
	private String tutor;
	private String nombre;
	private String calle;
	private String numero;
	private String colonia;
	private String codigoPostal;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String telefono;
	private String estado;
	private String ciudad;
	
	public AdmTutor(){
		folio 		= "";
		tutor		= "";
		nombre	 	= "";
		calle		= "";
		numero		= "";
		colonia		= "";
		codigoPostal= "";
		paisId		= "";
		estadoId	= "";
		ciudadId  	= "";
		telefono	= "";
		estado		= "";
		ciudad  	= "";
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
}