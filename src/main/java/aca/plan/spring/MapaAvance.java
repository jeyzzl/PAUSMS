package  aca.plan.spring;

public class MapaAvance{
	
	private String planId;
	private String ciclo;
	private String tipocursoId;
	private String creditos;
	
	public MapaAvance(){
		planId			= "";
		ciclo			= "";
		tipocursoId		= "";
		creditos		= "0";
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

	public String getTipocursoId() {
		return tipocursoId;
	}

	public void setTipocursoId(String tipocursoId) {
		this.tipocursoId = tipocursoId;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}	
}