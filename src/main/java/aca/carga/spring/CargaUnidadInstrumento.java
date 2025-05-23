package aca.carga.spring;

public class CargaUnidadInstrumento {
	private String cursoCargaId;
	private String instrumentoId;	
	private String instrumentoNombre;
	
	public CargaUnidadInstrumento(){
		cursoCargaId      = "";
		instrumentoId	  = "";
		instrumentoNombre = "";
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
	 * @return the instrumentoId
	 */
	public String getInstrumentoId() {
		return instrumentoId;
	}

	/**
	 * @param instrumentoId the instrumentoId to set
	 */
	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	/**
	 * @return the instrumentoNombre
	 */
	public String getInstrumentoNombre() {
		return instrumentoNombre;
	}

	/**
	 * @param instrumentoNombre the instrumentoNombre to set
	 */
	public void setInstrumentoNombre(String instrumentoNombre) {
		this.instrumentoNombre = instrumentoNombre;
	}
	
}