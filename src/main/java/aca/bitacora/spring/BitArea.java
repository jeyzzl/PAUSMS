package aca.bitacora.spring;

public class BitArea {
	
	private String areaId;
	private String areaNombre;
	private String responsable;
	private String acceso;
	
	public BitArea(){
		areaId 			= "0";
		areaNombre 		= "-";
		responsable 	= "-";
		acceso			= "-";
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

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}
	
}
