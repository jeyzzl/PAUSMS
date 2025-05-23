package aca.mentores.spring;

public class MentLog {
	private String folio;
	private String mentorId;
	private String codigoPersonal;
	private String fecha;
	private String tab;
	
	public MentLog(){
		folio			= "";
		mentorId		= "";
		codigoPersonal	= "";
		fecha			= "";
		tab				= "";
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
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}

	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	 * @return the tab
	 */
	public String getTab() {
		return tab;
	}

	/**
	 * @param tab the tab to set
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}

}