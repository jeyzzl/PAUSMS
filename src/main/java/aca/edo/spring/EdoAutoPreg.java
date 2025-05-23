package aca.edo.spring;

public class EdoAutoPreg {
	private String preguntaId;
	private String edoId;
	private String pregunta;
	private String tipo;
	private String orden;
	
	public EdoAutoPreg(){
		preguntaId	= "";
		edoId		= "";
		pregunta	= "";
		tipo		= "";
		orden		= "";
	}

	/**
	 * @return the preguntaId
	 */
	public String getPreguntaId() {
		return preguntaId;
	}

	/**
	 * @param preguntaId the preguntaId to set
	 */
	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	/**
	 * @return the edoId
	 */
	public String getEdoId() {
		return edoId;
	}

	/**
	 * @param edoId the edoId to set
	 */
	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}