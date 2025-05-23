package aca.trabajo.spring;

public class TrabInforme {
	private String informeId;
	private String nombreInforme;
	private String estado;
	private String periodoId;
	
	public TrabInforme() {
		setInformeId("0");
		setNombreInforme("-");
		setEstado("A");
		setPeriodoId("0");
	}

	public String getInformeId() {
		return informeId;
	}

	public void setInformeId(String informeId) {
		this.informeId = informeId;
	}

	public String getNombreInforme() {
		return nombreInforme;
	}

	public void setNombreInforme(String nombreInforme) {
		this.nombreInforme = nombreInforme;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
}
