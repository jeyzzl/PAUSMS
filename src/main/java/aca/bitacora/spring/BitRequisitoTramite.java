package aca.bitacora.spring;

public class BitRequisitoTramite {
	
	private String requisitoId;
	private String tramiteId;
	
	public BitRequisitoTramite(){
		requisitoId = "0";
		tramiteId = "0";
	}

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}
}
