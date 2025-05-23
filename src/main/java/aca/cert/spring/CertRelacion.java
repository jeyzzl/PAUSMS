/**
 * 
 */
package aca.cert.spring;

/**
 * @author Jose Torres
 *
 */
public class CertRelacion {
	private String cursoId;
	private String cursoCert;
	
	public CertRelacion(){
		cursoId		= "";
		cursoCert	= "";
	}
	
	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCursoCert() {
		return cursoCert;
	}

	public void setCursoCert(String cursoCert) {
		this.cursoCert = cursoCert;
	}
	
}