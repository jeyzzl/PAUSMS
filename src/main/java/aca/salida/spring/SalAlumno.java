package aca.salida.spring;

public class SalAlumno {

	private String salidaId;
	private String codigoPersonal;
	private String fecha;
	private String usuario;
	
	public SalAlumno(){
		salidaId     	= "";
		codigoPersonal 	= "";
		fecha  			= "";
		usuario  		= "";
		
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
	
}