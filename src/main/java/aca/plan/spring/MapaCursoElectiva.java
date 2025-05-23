// Bean del Catalogo Cursos Electivos
package  aca.plan.spring;

public class MapaCursoElectiva{
	
	private String cursoId;
	private String folio;
	private String cursoElec;	
	private String cursoNombre;
	
	public MapaCursoElectiva(){
		cursoId		= "";		
		folio		= "";
		cursoElec 	= "";
		cursoNombre	= "";
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the cursoElec
	 */
	public String getCursoElec() {
		return cursoElec;
	}

	/**
	 * @param cursoElec the cursoElec to set
	 */
	public void setCursoElec(String cursoElec) {
		this.cursoElec = cursoElec;
	}

	/**
	 * @return the cursoNombre
	 */
	public String getCursoNombre() {
		return cursoNombre;
	}

	/**
	 * @param cursoNombre the cursoNombre to set
	 */
	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
	}
	
}