package aca.admision.spring;

public class AdmRecomienda {
	private String folio;
	private String recomendacionId;
	private String nombre;
	private String puesto;
	private String email;
	private String telefono;
	private String estado;
	private String direccion;
		
	public AdmRecomienda(){
		folio 			= "0";
		recomendacionId = "0";
		nombre 			= "";
		puesto			= "";
		email 			= "";
		telefono 		= "";
		estado 			= "";
		direccion		= "";
	}	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRecomendacionId() {
		return recomendacionId;
	}

	public void setRecomendacionId(String recomendacionId) {
		this.recomendacionId = recomendacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}