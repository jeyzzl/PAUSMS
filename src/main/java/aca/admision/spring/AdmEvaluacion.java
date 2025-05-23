package aca.admision.spring;

public class AdmEvaluacion {

	private String evaluacionId;
	private String evaluacionNombre;
	private String acceso;
	private String estado;
	private String icono;
	private String puntos;
	
	public AdmEvaluacion() {
		evaluacionId 		= "0";
		evaluacionNombre 	= "-";
		acceso 				= "-";
		estado 				= "A";
		icono 				= "-";
		puntos				= "0";
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

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}
	
}
