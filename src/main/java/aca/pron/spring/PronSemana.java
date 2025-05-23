package aca.pron.spring;

public class PronSemana {
	
	private String cursoCargaId;
	private String unidadId;
	private String semanaId;
	private String semanaNombre;
	private String contenido;
	private String actividades;
	private String evidencias; 
	private String orden;
	
	public PronSemana() {
		cursoCargaId 	= "0";
		unidadId 		= "0";
		semanaId 		= "0";
		semanaNombre 	= "-";
		contenido 		= "-";
		actividades 	= "-";
		evidencias 		= "-";
		orden 			= "0";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getSemanaId() {
		return semanaId;
	}

	public void setSemanaId(String semanaId) {
		this.semanaId = semanaId;
	}

	public String getSemanaNombre() {
		return semanaNombre;
	}

	public void setSemanaNombre(String semanaNombre) {
		this.semanaNombre = semanaNombre;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public String getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(String evidencias) {
		this.evidencias = evidencias;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
