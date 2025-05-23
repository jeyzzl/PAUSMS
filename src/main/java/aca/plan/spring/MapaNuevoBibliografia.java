/**
 * 
 */
package aca.plan.spring;

/**
 * @author elifo
 *
 */
public class MapaNuevoBibliografia {
	private String cursoId;
	private String bibliografiaId;
	private String bibliografia;
	private String tipo;
	private String referencia;
	
	public MapaNuevoBibliografia(){
		cursoId			= "";
		bibliografiaId	= "";
		bibliografia	= "";
		tipo			= "";
		referencia		= "";
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
	 * @return the bibliografia
	 */
	public String getBibliografia() {
		return bibliografia;
	}

	/**
	 * @param bibliografia the bibliografia to set
	 */
	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
}