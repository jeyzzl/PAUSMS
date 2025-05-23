package aca.portafolio.spring;

public class PorCompromiso {
	
	private String codigoPersonal;
	private String periodoId;
	private String departamento;
	private String educar;
	private String modelar;
	private String investigar;
	private String servir;
	private String proclamar;	
	private String esperanza;
	private String estado;
	private String fecha;
	
	public PorCompromiso(){
		codigoPersonal 	= "0";
		periodoId 		= "0";
		departamento 	= "-";
		educar 			= "-";
		modelar 		= "-";
		investigar 		= "-";
		servir 			= "-";
		proclamar 		= "-";
		esperanza 		= "-";
		estado			= "-";
		fecha			= "-";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEducar() {
		return educar;
	}
	public void setEducar(String educar) {
		this.educar = educar;
	}

	public String getModelar() {
		return modelar;
	}
	public void setModelar(String modelar) {
		this.modelar = modelar;
	}

	public String getInvestigar() {
		return investigar;
	}
	public void setInvestigar(String investigar) {
		this.investigar = investigar;
	}

	public String getServir() {
		return servir;
	}
	public void setServir(String servir) {
		this.servir = servir;
	}

	public String getProclamar() {
		return proclamar;
	}
	public void setProclamar(String proclamar) {
		this.proclamar = proclamar;
	}

	public String getEsperanza() {
		return esperanza;
	}
	public void setEsperanza(String esperanza) {
		this.esperanza = esperanza;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
