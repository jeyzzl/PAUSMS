/**
 * 
 */
package aca.alumno.spring;


/**
 * @author Elifo
 *
 */
public class AlumAptitud {
	private String codigoPersonal;
	private String cargaId;
	private String fuerza;
	private String flexibilidad;
	private String resistencia;
	private String cardio;
	private String peso;
	private String talla;
	private String imc;
	private String grasa;
	private String abdomen;
	private String dieta;
	
	public AlumAptitud(){
		codigoPersonal	= "";
		cargaId			= "";
		fuerza			= "";
		flexibilidad	= "";
		resistencia		= "";
		cardio			= "";
		peso			= "";
		talla			= "";
		imc				= "";
		grasa			= "";
		abdomen			= "";
		dieta			= "";
	}

	/**
	 * @return the abdomen
	 */
	public String getAbdomen() {
		return abdomen;
	}

	/**
	 * @param abdomen the abdomen to set
	 */
	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	/**
	 * @return the cardio
	 */
	public String getCardio() {
		return cardio;
	}

	/**
	 * @param cardio the cardio to set
	 */
	public void setCardio(String cardio) {
		this.cardio = cardio;
	}

	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
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
	 * @return the flexibilidad
	 */
	public String getFlexibilidad() {
		return flexibilidad;
	}

	/**
	 * @param flexibilidad the flexibilidad to set
	 */
	public void setFlexibilidad(String flexibilidad) {
		this.flexibilidad = flexibilidad;
	}

	/**
	 * @return the fuerza
	 */
	public String getFuerza() {
		return fuerza;
	}

	/**
	 * @param fuerza the fuerza to set
	 */
	public void setFuerza(String fuerza) {
		this.fuerza = fuerza;
	}

	/**
	 * @return the grasa
	 */
	public String getGrasa() {
		return grasa;
	}

	/**
	 * @param grasa the grasa to set
	 */
	public void setGrasa(String grasa) {
		this.grasa = grasa;
	}

	/**
	 * @return the imc
	 */
	public String getImc() {
		return imc;
	}

	/**
	 * @param imc the imc to set
	 */
	public void setImc(String imc) {
		this.imc = imc;
	}

	/**
	 * @return the peso
	 */
	public String getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}

	/**
	 * @return the resistencia
	 */
	public String getResistencia() {
		return resistencia;
	}

	/**
	 * @param resistencia the resistencia to set
	 */
	public void setResistencia(String resistencia) {
		this.resistencia = resistencia;
	}

	/**
	 * @return the talla
	 */
	public String getTalla() {
		return talla;
	}

	/**
	 * @param talla the talla to set
	 */
	public void setTalla(String talla) {
		this.talla = talla;
	}
	
	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
	}
	
}