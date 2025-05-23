package aca.admision.spring;

public class AdmSalud {
	private String folio;
	private String enfermedad;
	private String enfermedadDato;
	private String impedimento;
	private String impedimentoDato;
	
	public AdmSalud(){
		folio 			= "";
		enfermedad		= "";
		enfermedadDato 	= "";
		impedimento		= "";
		impedimentoDato	= "";
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public String getEnfermedadDato() {
		return enfermedadDato;
	}

	public void setEnfermedadDato(String enfermedadDato) {
		this.enfermedadDato = enfermedadDato;
	}

	public String getImpedimento() {
		return impedimento;
	}

	public void setImpedimento(String impedimento) {
		this.impedimento = impedimento;
	}

	public String getImpedimentoDato() {
		return impedimentoDato;
	}

	public void setImpedimentoDato(String impedimentoDato) {
		this.impedimentoDato = impedimentoDato;
	}
}