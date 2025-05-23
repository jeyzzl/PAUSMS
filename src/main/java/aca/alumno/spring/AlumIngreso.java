package aca.alumno.spring;

public class AlumIngreso {

	private String codigoPersonal;
	private String planId;
	private String cargaId;
	private String carreraId;
	private String newUm;
	private String newPlan;
	
	public AlumIngreso(){
		codigoPersonal	= "";
		planId	        = "";
		cargaId			= "";
		carreraId    	= "";
		newUm	    	= "";
		newPlan		    = "";
		
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getNewUm() {
		return newUm;
	}

	public void setNewUm(String newUm) {
		this.newUm = newUm;
	}

	public String getNewPlan() {
		return newPlan;
	}

	public void setNewPlan(String newPlan) {
		this.newPlan = newPlan;
	}
}