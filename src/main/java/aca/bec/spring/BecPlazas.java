package aca.bec.spring;

public class BecPlazas {
	private String idEjercicio;
	private String idCcosto;
	private String numPlazas;
	private String numIndustriales;
	private String numTemporales;
	private String numPreIndustriales;
	private String numPosgrado;
	private String periodoId;
	private String contacto;
	private String telefono;
	private String correo;
	
	public BecPlazas(){
		idEjercicio		= "";
		idCcosto		= "";
		numPlazas		= "0";
		numIndustriales = "0";
		numTemporales	= "0";
		numPreIndustriales	= "0";
		numPosgrado		= "0";
		periodoId		= "";
		contacto		= "";
		telefono		= "";
		correo			= "";
	}
	

	public String getIdEjercicio() {
		return idEjercicio;
	}


	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}


	public String getIdCcosto() {
		return idCcosto;
	}


	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}


	public String getNumPlazas() {
		return numPlazas;
	}


	public void setNumPlazas(String numPlazas) {
		this.numPlazas = numPlazas;
	}
	
	public String getNumTemporales() {
		return numTemporales;
	}


	public void setNumTemporales(String numTemporales) {
		this.numTemporales= numTemporales;
	}

	public String getNumIndustriales() {
		return numIndustriales;
	}


	public void setNumIndustriales(String numIndustriales) {
		this.numIndustriales = numIndustriales;
	}

	public String getNumPreIndustriales() {
		return numPreIndustriales;
	}


	public void setNumPreIndustriales(String numPreIndustriales) {
		this.numPreIndustriales = numPreIndustriales;
	}


	public String getNumPosgrado() {
		return numPosgrado;
	}


	public void setNumPosgrado(String numPosgrado) {
		this.numPosgrado = numPosgrado;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}


	public String getContacto() {
		return contacto;
	}


	public void setContacto(String contacto) {
		this.contacto = contacto;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}
			
}