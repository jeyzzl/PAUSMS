// Clase para la vista ESTINTERNOS
package  aca.vista.spring;

public class EstInternos{
	private String facultadId;
	private String carreraId;
	private String codigoPersonal;
	private String dormitorio;	
	
	public EstInternos(){
		facultadId		= "";
		carreraId		= "";
		codigoPersonal	= "";
		dormitorio		= "";		
	}	
	
	/**
	 * @return Returns the carreraId.
	 */
	public String getCarreraId() {
		return carreraId;
	}
	/**
	 * @param carreraId The carreraId to set.
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	/**
	 * @return Returns the facultadId.
	 */
	public String getFacultadId() {
		return facultadId;
	}
	/**
	 * @param facultadId The facultadId to set.
	 */
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}
	/**
	 * @return Returns the dormitorio.
	 */
	public String getDormitorio() {
		return dormitorio;
	}
	/**
	 * @param dormitorio The dormitorio to set.
	 */
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
}