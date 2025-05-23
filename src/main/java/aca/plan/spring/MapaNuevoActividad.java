/**
 * 
 */
package aca.plan.spring;

/**
 * @author elifo
 *
 */
public class MapaNuevoActividad {
	private String cursoId;
	private String unidadId;
	private String actividadId;
	private String objetivo;
	private String descripcion;
	private String criterio;
	private String tipo;
	
	public MapaNuevoActividad(){
		cursoId			= "";
		unidadId		= "";
		actividadId		= "";
		objetivo		= "";
		descripcion		= "";
		criterio		= "";
		tipo			= "";
	}
	
	
	public String getCursoId() {
		return cursoId;
	}


	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}


	public String getUnidadId() {
		return unidadId;
	}


	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}


	public String getActividadId() {
		return actividadId;
	}


	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}


	public String getObjetivo() {
		return objetivo;
	}


	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getCriterio() {
		return criterio;
	}


	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}