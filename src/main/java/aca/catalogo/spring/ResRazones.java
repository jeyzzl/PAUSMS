package aca.catalogo.spring;

public class ResRazones {
	
	private int razon;
	private String descripcion;
	
	public ResRazones() {
		razon 		= 0;
		descripcion = "";
	}

	public int getRazon() {
		return razon;
	}

	public void setRazon(int razon) {
		this.razon = razon;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
