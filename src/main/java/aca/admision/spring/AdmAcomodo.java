package aca.admision.spring;

public class AdmAcomodo {
	private String acomodoId;
	private String acomodoNombre;
	private String acomodoTipo;
		
	public AdmAcomodo(){
		acomodoId 			= "";
		acomodoNombre 		= "";
		acomodoTipo 		= "";
	}

	public String getAcomodoId() {
		return acomodoId;
	}
	public void setAcomodoId(String acomodoId) {
		this.acomodoId = acomodoId;
	}

	public String getAcomodoNombre() {
		return acomodoNombre;
	}
	public void setAcomodoNombre(String acomodoNombre) {
		this.acomodoNombre = acomodoNombre;
	}

	public String getAcomodoTipo() {
		return acomodoTipo;
	}
	public void setAcomodoTipo(String acomodoTipo) {
		this.acomodoTipo = acomodoTipo;
	}

	
	
}