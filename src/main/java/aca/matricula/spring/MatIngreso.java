package aca.matricula.spring;

public class MatIngreso {
	private String eventoId;
	private String matricula;
	private String propios;
	private String becario;	
	private String colportaje;
	private String otro;
	
	public MatIngreso(){
		eventoId		= "0";
		matricula		= "0";
		propios			= "0";		
		becario			= "0";		
		colportaje		= "0";	
		otro			= "0";	
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

	public String getPropios() {
		return propios;
	}

	public void setPropios(String propios) {
		this.propios = propios;
	}

	public String getBecario() {
		return becario;
	}

	public void setBecario(String becario) {
		this.becario = becario;
	}

	public String getColportaje() {
		return colportaje;
	}

	public void setColportaje(String colportaje) {
		this.colportaje = colportaje;
	}

	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}
}