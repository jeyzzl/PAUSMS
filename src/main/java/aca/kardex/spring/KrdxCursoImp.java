// Bean de Cursos Importados
package  aca.kardex.spring;


public class KrdxCursoImp{
	private String codigoPersonal;
	private String folio;
	private String fCreada;
	private String cursoId;
	private String cursoId2;
	private String convalidacion;
	private String titulo;
	private String optativa;
	private String tipoCalId;
	private String nota;
	private String notaExtra;
	private String fExtra;
	private String notaConva;
	private String observaciones;
	private String optativaNombre;
	private String usuario;
	private String fecha;
	private String ciclo;
	
	
	public KrdxCursoImp(){
		codigoPersonal	= "0";
		folio			= "0";
		fCreada			= aca.util.Fecha.getHoy();
		cursoId			= "0";
		cursoId2		= "0";
		convalidacion	= "N";
		titulo			= "N";
		optativa		= "N";
		tipoCalId		= "1";
		nota			= "0";
		notaExtra		= "0";
		fExtra			= "";
		notaConva 		= "0";
		observaciones	= "-";
		optativaNombre	= "-";
		usuario			= "0";
		fecha			= aca.util.Fecha.getHoy();
		ciclo			= "0";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	 * @return the fCreada
	 */
	public String getFCreada() {
		return fCreada;
	}
	
	/**
	 * @param creada the fCreada to set
	 */
	public void setFCreada(String creada) {
		fCreada = creada;
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
	 * @return the cursoId2
	 */
	public String getCursoId2() {
		return cursoId2;
	}

	/**
	 * @param cursoId2 the cursoId2 to set
	 */
	public void setCursoId2(String cursoId2) {
		this.cursoId2 = cursoId2;
	}

	/**
	 * @return the convalidacion
	 */
	public String getConvalidacion() {
		return convalidacion;
	}

	/**
	 * @param convalidacion the convalidacion to set
	 */
	public void setConvalidacion(String convalidacion) {
		this.convalidacion = convalidacion;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the optativa
	 */
	public String getOptativa() {
		return optativa;
	}

	/**
	 * @param optativa the optativa to set
	 */
	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}


	/**
	 * @return the tipoCalId
	 */
	public String getTipoCalId() {
		return tipoCalId;
	}

	/**
	 * @param tipoCalId the tipoCalId to set
	 */
	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}
	
	public String getNota() {
		return nota;
	}	
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getNotaExtra() {
		return notaExtra;
	}	
	public void setNotaExtra(String notaExtra) {
		this.notaExtra = notaExtra;
	}
	
	public String getFExtra() {
		return fExtra;
	}
	public void setFExtra(String extra) {
		fExtra = extra;
	}
	
	public String getNotaConva() {
		return notaConva;
	}	
	public void setNotaConva(String notaConva) {
		this.notaConva = notaConva;
	}
	
	public String getObservaciones() {
		return observaciones;
	}	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public String getOptativaNombre() {
		return optativaNombre;
	}	
	public void setOptativaNombre(String optativaNombre) {
		this.optativaNombre = optativaNombre;
	}	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	

}