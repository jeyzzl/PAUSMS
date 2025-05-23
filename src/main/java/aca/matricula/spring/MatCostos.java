package aca.matricula.spring;

public class MatCostos {
	private String eventoId;
	private String matricula;
	private String creditos;
	private String cargaId;
	
	public MatCostos(){
		eventoId		= "0";
		matricula		= "0";
		creditos		= "0";
		cargaId			= "0";
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	
}