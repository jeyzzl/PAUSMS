package aca.cred.spring;

public class CredHsm {
	
	private String clave;
	private String nombre;
	private String area;
	private String fondo;
	private String estado;
			
	public CredHsm(){
		clave 		= "";
		nombre		= "";
		area		= "";
		fondo		= "";
		estado 		= "";
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


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getFondo() {
		return fondo;
	}


	public void setFondo(String fondo) {
		this.fondo = fondo;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

}