package aca.rotaciones.spring;

public class RotHospital {

	private String hospitalId;
	private String hospitalNombre;
	private String hospitalCorto;
	private String institucionId;
	private String calle;
	private String colonia;
	private String munEdo;
	private String pais;
	private String telefono;
	private String fax;
	private String medico;
	private String puesto;	
	private String saludo;
	
	public RotHospital(){		
		hospitalId		= "0";
		hospitalNombre	= "-";
		hospitalCorto	= "-";
		institucionId 	= "0";
		calle			= "-";
		colonia			= "-";
		munEdo			= "-";
		pais			= "-";
		telefono		= "-";
		fax				= "-";
		medico			= "-";
		puesto			= "-";
		saludo			= "-";
	}
	
	

	public String getHospitalId() {
		return hospitalId;
	}



	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}



	public String getHospitalNombre() {
		return hospitalNombre;
	}



	public void setHospitalNombre(String hospitalNombre) {
		this.hospitalNombre = hospitalNombre;
	}



	public String getHospitalCorto() {
		return hospitalCorto;
	}



	public void setHospitalCorto(String hospitalCorto) {
		this.hospitalCorto = hospitalCorto;
	}



	public String getInstitucionId() {
		return institucionId;
	}



	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}



	public String getCalle() {
		return calle;
	}



	public void setCalle(String calle) {
		this.calle = calle;
	}



	public String getColonia() {
		return colonia;
	}



	public void setColonia(String colonia) {
		this.colonia = colonia;
	}



	public String getMunEdo() {
		return munEdo;
	}



	public void setMunEdo(String munEdo) {
		this.munEdo = munEdo;
	}



	public String getPais() {
		return pais;
	}



	public void setPais(String pais) {
		this.pais = pais;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getFax() {
		return fax;
	}



	public void setFax(String fax) {
		this.fax = fax;
	}



	public String getMedico() {
		return medico;
	}



	public void setMedico(String medico) {
		this.medico = medico;
	}



	public String getPuesto() {
		return puesto;
	}



	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}



	public String getSaludo() {
		return saludo;
	}



	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}
	
}
