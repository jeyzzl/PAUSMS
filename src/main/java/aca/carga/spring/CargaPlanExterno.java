package aca.carga.spring;

public class CargaPlanExterno {
	private String cargaId;	
	private String planId;
		
	public CargaPlanExterno(){
		cargaId      = "";	
		planId       = "";
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
}