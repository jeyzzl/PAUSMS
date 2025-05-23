// Bean del Catalogo de Grupos
package  aca.carga.spring;

public class CargaGrupoBorra{
	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String usuario;
	private String ip;
	private String numEst;
	private String numAct;
	
	public CargaGrupoBorra(){
		cursoCargaId	= "";
		folio			= "0";		
		fecha			= "";
		usuario			= "";
		ip				= "";
		numEst			= "";
		numAct			= "";		
	}	
	
	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the numEst
	 */
	public String getNumEst() {
		return numEst;
	}

	/**
	 * @param numEst the numEst to set
	 */
	public void setNumEst(String numEst) {
		this.numEst = numEst;
	}

	/**
	 * @return the numAct
	 */
	public String getNumAct() {
		return numAct;
	}

	/**
	 * @param numAct the numAct to set
	 */
	public void setNumAct(String numAct) {
		this.numAct = numAct;
	}	
	
}