//asdasdsd
package aca.bec.spring;

public class BecPrecio {
	private String idEjercicio;
	private String precio;
	
	public BecPrecio(){
		idEjercicio		= "";
		precio			= "";		
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}	
}