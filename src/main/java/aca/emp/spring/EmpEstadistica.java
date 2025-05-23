package aca.emp.spring;

public class EmpEstadistica {
	private String codigoPersonal;
	private String cargas;
	private String modalidades;
	
	public EmpEstadistica(){
		codigoPersonal	= "";
		cargas	        = "";
		modalidades	    = "";
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
	 * @return the cargas
	 */
	public String getCargas() {
		return cargas;
	}

	/**
	 * @param cargas the cargas to set
	 */
	public void setCargas(String cargas) {
		this.cargas = cargas;
	}

	/**
	 * @return the modalidades
	 */
	public String getModalidades() {
		return modalidades;
	}

	/**
	 * @param modalidades the modalidades to set
	 */
	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}
	
}