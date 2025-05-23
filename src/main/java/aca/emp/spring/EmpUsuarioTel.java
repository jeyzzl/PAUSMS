package aca.emp.spring;

public class EmpUsuarioTel {
	private String codigoPersonal;
	private String telefono;
	private String extension;
	private String lugar;
	
	public EmpUsuarioTel(){
		codigoPersonal		= "";
		telefono	    	= "";
		extension	    	= "";
		lugar				= "";
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
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}


	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}


	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}


	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}


	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}