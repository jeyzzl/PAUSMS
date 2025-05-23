package aca.admision.spring;

public class AdmOpcionPlan {
	private String opcionId;
	private String planId;
		
	public AdmOpcionPlan(){
		opcionId 	= "0";
		planId 		= "0";
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

}