package aca.financiero.spring;

public class FinCreditos {
	
	private String cargaId;
	private String deptoId;	
	private String norAcfe;
	private String norNoAfce;
	private String diaAcfe;
	private String diaNoAcfe;
	
	public FinCreditos() {
		cargaId 	= "";
		deptoId 	= "";	
		norAcfe 	= "";
		norNoAfce 	= "";
		diaAcfe 	= "";
		diaNoAcfe 	= "";
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getDeptoId() {
		return deptoId;
	}

	public void setDeptoId(String deptoId) {
		this.deptoId = deptoId;
	}

	public String getNorAcfe() {
		return norAcfe;
	}

	public void setNorAcfe(String norAcfe) {
		this.norAcfe = norAcfe;
	}

	public String getNorNoAfce() {
		return norNoAfce;
	}

	public void setNorNoAfce(String norNoAfce) {
		this.norNoAfce = norNoAfce;
	}

	public String getDiaAcfe() {
		return diaAcfe;
	}

	public void setDiaAcfe(String diaAcfe) {
		this.diaAcfe = diaAcfe;
	}

	public String getDiaNoAcfe() {
		return diaNoAcfe;
	}

	public void setDiaNoAcfe(String diaNoAcfe) {
		this.diaNoAcfe = diaNoAcfe;
	}
}
