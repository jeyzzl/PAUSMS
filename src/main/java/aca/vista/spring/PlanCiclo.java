/**
 * 
 */
package aca.vista.spring;


/**
 * @author general
 *
 */
public class PlanCiclo {
	private String planId;
	private String ciclo;
	private String creditos;
	
	public PlanCiclo(){
		planId		= "";
		ciclo		= "";
		creditos	= "";
	}

	
public String getPlanId() {
		return planId;
	}


	public void setPlanId(String planId) {
		this.planId = planId;
	}


	public String getCiclo() {
		return ciclo;
	}


	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}


	public String getCreditos() {
		return creditos;
	}


	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

}