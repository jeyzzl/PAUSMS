package aca.alumno.spring;

public class AlumAsesor{
	
	private String codigoPersonal;
	private String planId;
	private String asesorId;
	
	public AlumAsesor(){		
		codigoPersonal	= "0";
		planId			= "0";
		asesorId		= "0";
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
	public String getAsesorId() {
		return asesorId;
	}
	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}	
}