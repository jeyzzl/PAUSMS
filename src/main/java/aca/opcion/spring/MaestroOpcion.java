/**
 * 
 */
package aca.opcion.spring;

public class MaestroOpcion {
	private String codigoPersonal;
	private String vistaEvaluar;
	
	public MaestroOpcion(){
		codigoPersonal	= "";
		vistaEvaluar	= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getVistaEvaluar() {
		return vistaEvaluar;
	}

	public void setVistaEvaluar(String vistaEvaluar) {
		this.vistaEvaluar = vistaEvaluar;
	}
	
}