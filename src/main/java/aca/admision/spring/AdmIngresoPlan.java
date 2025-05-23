package aca.admision.spring;

public class AdmIngresoPlan {
	private String periodoId;
	private String modalidadId;
	private String planId;

	
	public AdmIngresoPlan(){
		periodoId 		= "0";
		modalidadId		= "0";
		planId		 	= "-";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getModalidadId() {
		return modalidadId;
	}
	
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}
	
	public String getPlanId() {
		return planId;
	}
	
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
}