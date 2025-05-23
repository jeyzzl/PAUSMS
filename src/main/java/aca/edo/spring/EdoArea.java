package aca.edo.spring;

public class EdoArea {
	private String areaId;
	private String areaNombre;
	private String areaTitulo;
	
	public EdoArea(){
		areaId				= "";
		areaNombre			= "";
		areaTitulo 			= "";
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
	
	public String getAreaTitulo() {
		return areaTitulo;
	}

	public void setAreaTitulo(String areaTitulo) {
		this.areaTitulo = areaTitulo;
	}
	
}