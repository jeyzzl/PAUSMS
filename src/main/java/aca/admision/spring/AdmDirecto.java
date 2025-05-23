package aca.admision.spring;

public class AdmDirecto {
	private String folio;
	private String matricula;
	private String planId;
	private String reciente;
	private String tetra;
	private byte[] recPrepa;
	private byte[] recVre;
	private String envioSol;
	private String prepaValido;
	private String vreValido;
	private String nombrePrepa;
	private String nombreVre;
		
	public AdmDirecto(){
		folio 		= "0";
		matricula 	= "1";
		planId 		= "1";
		reciente	= "-";
		tetra		= "-";
		recPrepa	= null;
		recVre		= null;
		envioSol	= "N";
		prepaValido	= "-";
		vreValido	= "-";
		nombrePrepa	= "";
		nombreVre	= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getReciente() {
		return reciente;
	}

	public void setReciente(String reciente) {
		this.reciente = reciente;
	}

	public String getTetra() {
		return tetra;
	}

	public void setTetra(String tetra) {
		this.tetra = tetra;
	}

	public byte[] getRecPrepa() {
		return recPrepa;
	}

	public void setRecPrepa(byte[] recPrepa) {
		this.recPrepa = recPrepa;
	}

	public byte[] getRecVre() {
		return recVre;
	}

	public void setRecVre(byte[] recVre) {
		this.recVre = recVre;
	}

	public String getEnvioSol() {
		return envioSol;
	}

	public void setEnvioSol(String envioSol) {
		this.envioSol = envioSol;
	}

	public String getPrepaValido() {
		return prepaValido;
	}

	public void setPrepaValido(String prepaValido) {
		this.prepaValido = prepaValido;
	}

	public String getVreValido() {
		return vreValido;
	}

	public void setVreValido(String vreValido) {
		this.vreValido = vreValido;
	}

	public String getNombrePrepa() {
		return nombrePrepa;
	}

	public void setNombrePrepa(String nombrePrepa) {
		this.nombrePrepa = nombrePrepa;
	}

	public String getNombreVre() {
		return nombreVre;
	}

	public void setNombreVre(String nombreVre) {
		this.nombreVre = nombreVre;
	}
	
}