// Clase para la vista ESTEXTERNOS
package  aca.vista.spring;

public class EstExternos{
	private String facultadId;
	private String carreraId;
	private String codigoPersonal;
	private String razon;
	private String fecha;
	
	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public EstExternos(){
		facultadId		= "";
		carreraId		= "";
		codigoPersonal	= "";
		razon			= "";
		fecha			= "";
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
	 * @return Returns the razon.
	 */
	public String getRazon() {
		return razon;
	}
	/**
	 * @param razon The razon to set.
	 */
	public void setRazon(String razon) {
		this.razon = razon;
	}
	
}