package aca.carga.spring;

public class CargaUnidadCriterio {
	private String cursoCargaId;
	private String criterioId;	
	private String criterioNombre;
	
	public CargaUnidadCriterio(){
		cursoCargaId   = "";
		criterioId	   = "";
		criterioNombre = "";
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
	 * @return the criterioId
	 */
	public String getCriterioId() {
		return criterioId;
	}

	/**
	 * @param criterioId the criterioId to set
	 */
	public void setCriterioId(String criterioId) {
		this.criterioId = criterioId;
	}

	/**
	 * @return the criterioNombre
	 */
	public String getCriterioNombre() {
		return criterioNombre;
	}

	/**
	 * @param criterioNombre the criterioNombre to set
	 */
	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}

}