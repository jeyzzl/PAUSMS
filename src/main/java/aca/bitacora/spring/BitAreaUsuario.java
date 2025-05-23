package aca.bitacora.spring;

public class BitAreaUsuario {
	
	private String areaId;
	private String codigoPersonal;
	
	public BitAreaUsuario(){
		areaId 			= "0";
		codigoPersonal  = "0";
	}
	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
}
