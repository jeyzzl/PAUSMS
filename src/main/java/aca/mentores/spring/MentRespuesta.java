package aca.mentores.spring;

public class MentRespuesta {
	private String codigoPersonal;
	private String preguntaId;
	private String importancia;
	private String satisfaccion;
	private String encuestaId;
	
	public MentRespuesta(){
		codigoPersonal	= "0";
		preguntaId		= "0";
		importancia		= "0";
		satisfaccion	= "0";
		encuestaId		= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getImportancia() {
		return importancia;
	}

	public void setImportancia(String importancia) {
		this.importancia = importancia;
	}

	public String getSatisfaccion() {
		return satisfaccion;
	}

	public void setSatisfaccion(String satisfaccion) {
		this.satisfaccion = satisfaccion;
	}

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}
}