package aca.inscrito.spring;

public class InsAlumno {

	private String codigoPersonal;
	private String cargaId;
	private String planId;
	private String ciclo;
	private String materias;

	
	public InsAlumno(){
		codigoPersonal 	= "-";
		cargaId 		= "-";
		planId			= "-";
		ciclo			= "0";
		materias		= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getMaterias() {
		return materias;
	}

	public void setMaterias(String materias) {
		this.materias = materias;
	}	
	
	
	
}
