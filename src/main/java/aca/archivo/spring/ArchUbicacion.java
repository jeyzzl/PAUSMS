//Beans de la tabla ARCH_UBICACION
package aca.archivo.spring;

public class ArchUbicacion {
    private String ubicacionId;
	private String ubicacionNombre;	

	public ArchUbicacion(){
		ubicacionId 	= "0";
		ubicacionNombre = "-";
	}
	
	public String getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(String ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public String getUbicacionNombre() {
		return ubicacionNombre;
	}

	public void setUbicacionNombre(String ubicacionNombre) {
		this.ubicacionNombre = ubicacionNombre;
	}
}