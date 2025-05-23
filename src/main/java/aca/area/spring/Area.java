package aca.area.spring;

public class Area {	
	
	private String areaId;
	private String areaNombre;
	private String responsable;
	
	public Area(){
		areaId			= "0";
		areaNombre		= "";
		responsable		= "";		 
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaNombre() {
		return areaNombre;
	}

	public void setAreaNombre(String areaNombre) {
		this.areaNombre = areaNombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
}