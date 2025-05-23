package aca.carga.spring;

public class CargaPlanEval {
	private String cursoCargaId;	
	private String evaluacionId;
	private String evaluacionNombre;
	private String fecha;
	private String valor;
	private String actividadId;
		
	public CargaPlanEval(){
	    cursoCargaId        = "";	
	    evaluacionId        = "";
	    evaluacionNombre    = "";
	    fecha               = "";
		valor               = "";
		actividadId 		= ""; 
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getEvaluacionNombre() {
		return evaluacionNombre;
	}

	public void setEvaluacionNombre(String evaluacionNombre) {
		this.evaluacionNombre = evaluacionNombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	

	/**
	 * @return the actividadId
	 */
	public String getActividadId() {
		return actividadId;
	}

	/**
	 * @param actividadId the actividadId to set
	 */
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}	
	
}