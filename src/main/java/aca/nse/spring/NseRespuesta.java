package aca.nse.spring;

public class NseRespuesta{
	private String preguntaId;
	private String codigoPersonal;
	private String respuesta;	
	private String puntos;	
	private String encuestaId;
	
	public NseRespuesta(){		
		preguntaId 		= "0";
		codigoPersonal 	= "0";
		respuesta 		= "";		
		puntos	 		= "";		
		encuestaId 		= "0";		
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}
}