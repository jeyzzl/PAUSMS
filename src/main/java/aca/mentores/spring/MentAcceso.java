/**
 * 
 */
package aca.mentores.spring;

/**
 * @author Elifo
 *
 */
public class MentAcceso {
	private String codigoPersonal;
	private String acceso;
	
	public MentAcceso(){
		codigoPersonal	= "0";
		acceso			= "-";
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
}