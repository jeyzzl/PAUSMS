package aca.sep.spring;

public class SepLugar {
	
	private String lugarId;
	private String lugarNombre;
	private String orden;
	
	public SepLugar() {
		lugarId 		= "";
		lugarNombre		= "";
		orden 			= "";
	}

	public String getLugarId() {
		return lugarId;
	}

	public void setLugarId(String lugarId) {
		this.lugarId = lugarId;
	}

	public String getLugarNombre() {
		return lugarNombre;
	}

	public void setLugarNombre(String lugarNombre) {
		this.lugarNombre = lugarNombre;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}
