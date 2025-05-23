package aca.nse.spring;

public class NsePregunta{
	private String preguntaId;
	private String encuestaId;
	private String pregunta;	
	
	public NsePregunta(){		
		preguntaId 		= "0";
		encuestaId 		= "0";
		pregunta 		= "";		
	}

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
}