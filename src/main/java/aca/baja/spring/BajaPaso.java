package aca.baja.spring;

public class BajaPaso {
	private String pasoId;
	private String pasoNombre;
	
	public BajaPaso(){
		pasoId		= "";
		pasoNombre	= "";
	}

	/**
	 * @return the pasoId
	 */
	public String getPasoId() {
		return pasoId;
	}

	/**
	 * @param pasoId the pasoId to set
	 */
	public void setPasoId(String pasoId) {
		this.pasoId = pasoId;
	}

	/**
	 * @return the pasoNombre
	 */
	public String getPasoNombre() {
		return pasoNombre;
	}

	/**
	 * @param pasoNombre the pasoNombre to set
	 */
	public void setPasoNombre(String pasoNombre) {
		this.pasoNombre = pasoNombre;
	}
	
}