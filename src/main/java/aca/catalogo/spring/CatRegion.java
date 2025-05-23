package aca.catalogo.spring;

public class CatRegion {
	private String regionId;
	private String culturalId;
	private String nombreRegion;
	
	public CatRegion () {
		regionId				= "0";
		culturalId				= "0";
		nombreRegion			= "-";
	}

	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getCulturalId() {
		return culturalId;
	}
	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	public String getNombreRegion() {
		return nombreRegion;
	}
	public void setNombreRegion(String nombreRegion) {
		this.nombreRegion = nombreRegion;
	}

}