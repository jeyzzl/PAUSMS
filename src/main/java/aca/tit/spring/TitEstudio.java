/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

public class TitEstudio {
	
    String estudioId;
    String estudioNombre;
    String estudioTipo;

    public TitEstudio() {
    	
    	estudioId 	 	= "";
    	estudioNombre	= "";
    	estudioTipo		= "";
    }

	public String getEstudioId() {
		return estudioId;
	}

	public void setEstudioId(String estudioId) {
		this.estudioId = estudioId;
	}

	public String getEstudioNombre() {
		return estudioNombre;
	}

	public void setEstudioNombre(String estudioNombre) {
		this.estudioNombre = estudioNombre;
	}

	public String getEstudioTipo() {
		return estudioTipo;
	}

	public void setEstudioTipo(String estudioTipo) {
		this.estudioTipo = estudioTipo;
	}
   	
}