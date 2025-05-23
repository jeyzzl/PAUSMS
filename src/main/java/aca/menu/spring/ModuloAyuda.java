/**
 * 
 */
package aca.menu.spring;

public class ModuloAyuda {
	private String opcionId;
	private String ayudaId;
	private String ayuda;
	
	public ModuloAyuda(){
		opcionId 	= "0";
		ayudaId		= "0";
		ayuda		= "-";
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	public String getAyudaId() {
		return ayudaId;
	}

	public void setAyudaId(String ayudaId) {
		this.ayudaId = ayudaId;
	}

	public String getAyuda() {
		return ayuda;
	}

	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}
	
}