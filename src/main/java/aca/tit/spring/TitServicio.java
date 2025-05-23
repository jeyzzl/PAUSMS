/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

public class TitServicio {
	
    String servicioId;
    String servicioNombre;

    public TitServicio() {
    	
    	servicioId 	 		= "";
    	servicioNombre		= "";
    }

	public String getServicioId() {
		return servicioId;
	}

	public void setServicioId(String servicioId) {
		this.servicioId = servicioId;
	}

	public String getServicioNombre() {
		return servicioNombre;
	}

	public void setServicioNombre(String servicioNombre) {
		this.servicioNombre = servicioNombre;
	}
   	
}