package aca.catalogo.spring;

public class CatCultural {
	private String culturalId;
	private String nombreCultural;
	private String principal;
	
	public CatCultural () {
		culturalId				= "0";
		nombreCultural			= "-";
		principal				= "-";
	}

	public String getCulturalId() {
		return culturalId;
	}
	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	public String getNombreCultural() {
		return nombreCultural;
	}
	public void setNombreCultural(String nombreCultural) {
		this.nombreCultural = nombreCultural;
	}

	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}