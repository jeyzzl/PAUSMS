package aca.saii.spring;

public class SaiiGrupo{
	
	private String grupoId		= "";
	private String periodoId	= "";
	private String planId		= "";	
	
	public SaiiGrupo(){		
		grupoId		= "0";
		periodoId	= "-";
		planId		= "A";		
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
}
