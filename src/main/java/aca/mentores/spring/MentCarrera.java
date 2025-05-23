/**
 * 
 */
package aca.mentores.spring;

/**
 * @author Elifo
 *
 */
public class MentCarrera {
	private String carreraId;
	private String mentorId;
	private String periodoId;
	
	public MentCarrera(){
		carreraId	= "";
		mentorId	= "";
		periodoId	= "";
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
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
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

}