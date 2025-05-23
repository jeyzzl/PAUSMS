package  aca.exa.spring;

public class ExaEmpleo{
	private String empleoId;	
	private String matricula;
	private String empresa;
	private String periodo;	
	private String fechaAct;
	private String eliminado;
	private String idEmpleo;
	
	public ExaEmpleo(){
		empleoId 			= "0";
		matricula			= "-";
		empresa 			= "-";
		periodo				= "-";
		fechaAct			= "-";
		eliminado			= "0";
		idEmpleo			= "-";
	}

	public String getEmpleoId() {
		return empleoId;
	}

	public void setEmpleoId(String empleoId) {
		this.empleoId = empleoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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

	public String getIdEmpleo() {
		return idEmpleo;
	}

	public void setIdEmpleo(String idEmpleo) {
		this.idEmpleo = idEmpleo;
	}
	
}