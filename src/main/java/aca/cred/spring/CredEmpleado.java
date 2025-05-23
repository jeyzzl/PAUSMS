package aca.cred.spring;

public class CredEmpleado {
	private String id;
	private String clave;
	private String nombre;
	private String apellidos;
	private String puesto;
	private String departamento;
	private String status;
	private String cotejado;
	private String imprimir;
	private String tipoCred;
	
	public CredEmpleado(){
		id 				= "";
		clave 			= "";
		nombre 			= "";
		apellidos 		= "";
		puesto 			= "";
		departamento 	= "";
		status 			= "";
		cotejado 		= "";
		imprimir 		= "";
		tipoCred 		= "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCotejado() {
		return cotejado;
	}

	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}

	public String getImprimir() {
		return imprimir;
	}

	public void setImprimir(String imprimir) {
		this.imprimir = imprimir;
	}

	public String getTipoCred() {
		return tipoCred;
	}

	public void setTipoCred(String tipoCred) {
		this.tipoCred = tipoCred;
	}
}
