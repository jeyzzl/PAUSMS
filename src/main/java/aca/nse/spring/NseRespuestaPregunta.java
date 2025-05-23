package aca.nse.spring;

public class NseRespuestaPregunta{
	private String respuestaId;
	private String encuestaId;
	private String preguntaId;
	private String respuesta;	
	private String puntos;	
	
	public NseRespuestaPregunta(){		
		respuestaId 	= "";		
		encuestaId 		= "0";
		preguntaId 		= "0";
		respuesta 		= "";		
		puntos 			= "";		
	}

	public String getRespuestaId() {
		return respuestaId;
	}

	public void setRespuestaId(String respuestaId) {
		this.respuestaId = respuestaId;
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

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	
}