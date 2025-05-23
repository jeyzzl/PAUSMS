// Beans para la tabla Res_Razones

package aca.residencia.spring;

public class ResRazon {
	private String razon;
	private String descripcion;
	
	public ResRazon(){
		razon		= "";
		descripcion = "";
	}

	public String getDescripcion() {
		return descripcion;
	}
	

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getRazon() {
		return razon;
	}
	

	public void setRazon(String razon) {
		this.razon = razon;
	}
	
}