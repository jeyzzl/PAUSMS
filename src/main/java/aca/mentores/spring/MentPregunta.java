package aca.mentores.spring;

public class MentPregunta {
	private String preguntaId;
	private String nombre;
	private String encuestaId;
	
	public MentPregunta(){
		preguntaId	= "0";
		nombre		= "-";
		encuestaId	= "0";
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}
}