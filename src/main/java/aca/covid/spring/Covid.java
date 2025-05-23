package aca.covid.spring;

public class Covid {
	
	private String codigoPersonal;
	private String periodoId;
	private String fecha;
	private String hipertension;
	private String diabetes;
	private String corazon;
	private String pulmon;
	private String cancer;
	private String obesidad;
	private String estres;
	private String depresion;
	private String imss;
	private String pase;
	private String isste;
	private String hlc;
	private String privado;
	private String ninguno;
	private String otro;
	
	public Covid() {
		codigoPersonal 	= "0";
		periodoId 		= "0";
		fecha			= aca.util.Fecha.getHoy();
		hipertension 	= "N";
		diabetes 		= "N";
		corazon 		= "N";
		pulmon 			= "N";
		cancer 			= "N";
		obesidad 		= "N";
		estres 			= "N";
		depresion 		= "N";
		imss 			= "N";
		pase 			= "N";
		isste 			= "N";
		hlc 			= "N";
		privado 		= "N";
		ninguno 		= "N";
		otro 			= "N";
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
	public String getHipertension() {
		return hipertension;
	}
	public void setHipertension(String hipertension) {
		this.hipertension = hipertension;
	}
	public String getDiabetes() {
		return diabetes;
	}
	public void setDiabetes(String diabetes) {
		this.diabetes = diabetes;
	}
	public String getCorazon() {
		return corazon;
	}
	public void setCorazon(String corazon) {
		this.corazon = corazon;
	}
	public String getPulmon() {
		return pulmon;
	}
	public void setPulmon(String pulmon) {
		this.pulmon = pulmon;
	}
	public String getCancer() {
		return cancer;
	}
	public void setCancer(String cancer) {
		this.cancer = cancer;
	}
	public String getObesidad() {
		return obesidad;
	}
	public void setObesidad(String obesidad) {
		this.obesidad = obesidad;
	}
	public String getEstres() {
		return estres;
	}
	public void setEstres(String estres) {
		this.estres = estres;
	}
	public String getDepresion() {
		return depresion;
	}
	public void setDepresion(String depresion) {
		this.depresion = depresion;
	}
	public String getImss() {
		return imss;
	}
	public void setImss(String imss) {
		this.imss = imss;
	}
	public String getPase() {
		return pase;
	}
	public void setPase(String pase) {
		this.pase = pase;
	}
	public String getIsste() {
		return isste;
	}
	public void setIsste(String isste) {
		this.isste = isste;
	}
	public String getHlc() {
		return hlc;
	}
	public void setHlc(String hlc) {
		this.hlc = hlc;
	}
	public String getPrivado() {
		return privado;
	}
	public void setPrivado(String privado) {
		this.privado = privado;
	}
	public String getNinguno() {
		return ninguno;
	}
	public void setNinguno(String ninguno) {
		this.ninguno = ninguno;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
}
