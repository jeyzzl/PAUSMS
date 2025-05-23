package aca.baja.spring;

public class BajaCurso {
	private String bajaId;
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String creditos;
	
	public BajaCurso(){
		bajaId			= "";
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		creditos		= "";
	}

	/**
	 * @return the bajaId
	 */
	public String getBajaId() {
		return bajaId;
	}

	/**
	 * @param bajaId the bajaId to set
	 */
	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
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
	 * @return the creditos
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}
	
}