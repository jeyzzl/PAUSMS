package aca.plan.spring;

public class MapaNuevoBiblioUnidad {
	private String cursoId;
	private String unidadId;
	private String bibliografiaId;
	private String id;
	private String especificacion;
	
	public MapaNuevoBiblioUnidad(){
		cursoId			= "";
		unidadId		= "";
		bibliografiaId	= "";
		id				= "";
		especificacion	= "";
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	/**
	 * @return the unidadId
	 */
	public String getUnidadId() {
		return unidadId;
	}

	/**
	 * @param unidadId the unidadId to set
	 */
	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	/**
	 * @return the bibliografiaId
	 */
	public String getBibliografiaId() {
		return bibliografiaId;
	}

	/**
	 * @param bibliografiaId the bibliografiaId to set
	 */
	public void setBibliografiaId(String bibliografiaId) {
		this.bibliografiaId = bibliografiaId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the especificacion
	 */
	public String getEspecificacion() {
		return especificacion;
	}

	/**
	 * @param especificacion the especificacion to set
	 */
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	
}