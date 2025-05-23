/**
 * 
 */
package aca.plan.spring;

/**
 * @author elifo
 *
 */
public class MapaNuevoUnidad {
	private String cursoId;
	private String unidadId;
	private String nombre;
	private String tiempo;
	private String temas;
	private String accionDocente;
	private String orden;
	
	public MapaNuevoUnidad(){
		cursoId				= "0";
		unidadId			= "0";
		nombre				= "-";
		tiempo				= "-";
		temas				= "-";
		accionDocente		= "-";
		orden 				= "0";
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getTemas() {
		return temas;
	}

	public void setTemas(String temas) {
		this.temas = temas;
	}

	public String getAccionDocente() {
		return accionDocente;
	}

	public void setAccionDocente(String accionDocente) {
		this.accionDocente = accionDocente;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}