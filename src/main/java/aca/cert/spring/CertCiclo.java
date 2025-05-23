/**
 * 
 */
package aca.cert.spring;

/**
 * @author elifo
 *
 */
public class CertCiclo {
	private String planId;
	private String cicloId;
	private String titulo;
	private String fst;
	private String fsp;
	private String creditos;
	
	public CertCiclo(){
		planId		= "";
		cicloId		= "";
		titulo		= "";
		fst			= "";
		fsp			= "";
		creditos	= "";
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCicloId() {
		return cicloId;
	}

	public void setCicloId(String cicloId) {
		this.cicloId = cicloId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFst() {
		return fst;
	}

	public void setFst(String fst) {
		this.fst = fst;
	}

	public String getFsp() {
		return fsp;
	}

	public void setFsp(String fsp) {
		this.fsp = fsp;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

}