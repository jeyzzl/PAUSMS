package aca.bitacora.spring;

public class BitTramiteRequisito {

	private String tramiteId;
	private String requisitoId;
	
	public BitTramiteRequisito(){
		tramiteId 	= "";
		requisitoId 	= "";
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

}
