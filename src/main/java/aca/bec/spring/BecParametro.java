package aca.bec.spring;

public class BecParametro {
	
	private String prepaInicio;
	private String prepaFinal;
	private String pregradoInicio;
	private String pregradoFinal;
	
	public BecParametro(){
		prepaInicio 	= aca.util.Fecha.getHoy();
		prepaFinal 		= aca.util.Fecha.getHoy();
		pregradoInicio 	= aca.util.Fecha.getHoy();
		pregradoFinal  	= aca.util.Fecha.getHoy();
	}
	
	public String getPrepaInicio() {
		return prepaInicio;
	}
	public void setPrepaInicio(String prepaInicio) {
		this.prepaInicio = prepaInicio;
	}
	public String getPrepaFinal() {
		return prepaFinal;
	}
	public void setPrepaFinal(String prepaFinal) {
		this.prepaFinal = prepaFinal;
	}
	public String getPregradoInicio() {
		return pregradoInicio;
	}
	public void setPregradoInicio(String pregradoInicio) {
		this.pregradoInicio = pregradoInicio;
	}
	public String getPregradoFinal() {
		return pregradoFinal;
	}
	public void setPregradoFinal(String pregradoFinal) {
		this.pregradoFinal = pregradoFinal;
	}
	
}