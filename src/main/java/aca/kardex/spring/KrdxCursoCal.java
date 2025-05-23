package aca.kardex.spring;

public class KrdxCursoCal {
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String fecha;
	private String fechaFinal; 
	private String nota;
	private String tipo;
	private String estado;
	private String tipoCalId;
	private String tipoNota;
	
	public KrdxCursoCal(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		fecha			= "";
		fechaFinal		= "";
		nota			= "";
		tipo 			= "";
		estado			= "";
		tipoCalId		= "";
		tipoNota		= "";
	}
	
	
	public String getTipoCalId() {
		return tipoCalId;
	}


	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}


	public String getTipoNota() {
		return tipoNota;
	}


	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCursoCargaId() {
		return cursoCargaId;
	}


	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}


	public String getCursoId() {
		return cursoId;
	}


	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}


	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
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

}
