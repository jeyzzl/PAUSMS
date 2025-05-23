package aca.salida.spring;

public class SalConsejero {
	private String salidaId;
	private String folio;
	private String consejero;
	private String trabajo;
	private String clave;
	
	public SalConsejero(){
		salidaId     = "";
		folio		 = "";
		consejero	 = "";
		trabajo		 = "";
		clave		 = "";
				
	}
	
	/**
	 * @return the salidaId
	 */
	public String getSalidaId() {
		return salidaId;
	}

	/**
	 * @param salidaId the salidaId to set
	 */
	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the consejero
	 */
	public String getConsejero() {
		return consejero;
	}

	/**
	 * @param consejero the consejero to set
	 */
	public void setConsejero(String consejero) {
		this.consejero = consejero;
	}

	/**
	 * @return the trabajo
	 */
	public String getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
}