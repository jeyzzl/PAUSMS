package  aca.exa.spring;

public class ExaEgreso{
	private String egresoId;	
	private String matricula;
	private String carreraId;
	private String year;	
	private String fechaAct;
	private String eliminado;
	private String idEgresadoAno;
	private String planId;
	
	public ExaEgreso(){
		egresoId 			= "0";
		matricula			= "0";
		carreraId 			= "0";
		year				= "0";
		fechaAct			= "";
		eliminado			= "";
		idEgresadoAno		= "";
		planId 				= "0";
	}
	
	public String getEgresoId() {
		return egresoId;
	}
	public void setEgresoId(String egresoId) {
		this.egresoId = egresoId;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getFechaAct() {
		return fechaAct;
	}
	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}
	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEgresadoAno() {
		return idEgresadoAno;
	}
	public void setIdEgresadoAno(String idEgresadoAno) {
		this.idEgresadoAno = idEgresadoAno;
	}
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}	
}