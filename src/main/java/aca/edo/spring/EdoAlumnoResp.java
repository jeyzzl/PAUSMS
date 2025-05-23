package aca.edo.spring;

public class EdoAlumnoResp {
	private String edoId;
	private String preguntaId;
	private String codigoPersonal;
	private String cursoCargaId;
	private String codigoMaestro;
	private String respuesta;
	
	public EdoAlumnoResp(){
		edoId			= "";
		preguntaId		= "";
		codigoPersonal	= "";
		cursoCargaId	= "";
		codigoMaestro	= "";
		respuesta		= "";
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
	 * @return the codigoMaestro
	 */
	public String getCodigoMaestro() {
		return codigoMaestro;
	}

	/**
	 * @param codigoMaestro the codigoMaestro to set
	 */
	public void setCodigoMaestro(String codigoMaestro) {
		this.codigoMaestro = codigoMaestro;
	}

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}