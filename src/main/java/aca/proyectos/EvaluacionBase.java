package aca.proyectos;

public class EvaluacionBase {
    
	String claveempleado;
	public String getClaveempleado() {
		return claveempleado;
	}


	public void setClaveempleado(String claveempleado) {
		this.claveempleado = claveempleado;
	}


	public String getClavejefe() {
		return clavejefe;
	}


	public void setClavejefe(String clavejefe) {
		this.clavejefe = clavejefe;
	}


	public String getCcosto() {
		return ccosto;
	}


	public void setCcosto(String ccosto) {
		this.ccosto = ccosto;
	}


	public String getAutoevaluacion() {
		return autoevaluacion;
	}


	public void setAutoevaluacion(String autoevaluacion) {
		this.autoevaluacion = autoevaluacion;
	}


	public String getJefeevalua() {
		return jefeevalua;
	}


	public void setJefeevalua(String jefeevalua) {
		this.jefeevalua = jefeevalua;
	}


	public String getEvaluajefe() {
		return evaluajefe;
	}


	public void setEvaluajefe(String evaluajefe) {
		this.evaluajefe = evaluajefe;
	}


	private String clavejefe;
	private String ccosto;
	private String autoevaluacion;
	private String jefeevalua;
	private String evaluajefe;
	private String nombreJefe;
	
	public String getNombreJefe() {
		return nombreJefe;
	}


	public void setNombreJefe(String nombreJefe) {
		this.nombreJefe = nombreJefe;
	}


	public EvaluacionBase() {
		// TODO Auto-generated constructor stub
	}

}
