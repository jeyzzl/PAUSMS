package aca.financiero.spring;

public class SunPlusFuncion {
	private String departamento;
	private String funcion;
	
	public SunPlusFuncion(){
		departamento	= "0000000";
		funcion			= "0";	
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public String getFuncion() {
		return funcion;
	}
	
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}	
}