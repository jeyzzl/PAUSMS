package aca.pg.archivo.spring;

public class PosArchDocAlum {
	private String matricula;
	private String iddocumento;
	private int imagen;
	private String hoja;
	private String fuente;
	private String tipo;
	private String origen;
	private String finsert;
	private String fupdate;
	private String usuario;
	private byte[] imagenByte;
	private String estado;
	
	public PosArchDocAlum(){
		matricula		= "0";
		iddocumento		= "0";
		imagen			= 0;
		hoja			= "1";
		fuente			= "C";
		tipo			= "";
		origen			= "";
		finsert			= "";
		fupdate			= "";
		usuario			= "0";
		estado 			= "L";
		imagenByte 		= null;
	}	
	
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}	

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}	

	/**
	 * @return the iddocumento
	 */
	public String getIddocumento() {
		return iddocumento;
	}

	/**
	 * @param iddocumento the iddocumento to set
	 */
	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	/**
	 * @return the imagen
	 */
	public int getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the hoja
	 */
	public String getHoja() {
		return hoja;
	}

	/**
	 * @param hoja the hoja to set
	 */
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	/**
	 * @return the fuente
	 */
	public String getFuente() {
		return fuente;
	}

	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(String fuente) {
		this.fuente = fuente;
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
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the finsert
	 */
	public String getFinsert() {
		return finsert;
	}

	/**
	 * @param finsert the finsert to set
	 */
	public void setFinsert(String finsert) {
		this.finsert = finsert;
	}

	/**
	 * @return the fupdate
	 */
	public String getFupdate() {
		return fupdate;
	}

	/**
	 * @param fupdate the fupdate to set
	 */
	public void setFupdate(String fupdate) {
		this.fupdate = fupdate;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public byte[] getImagenByte() {
		return imagenByte;
	}

	public void setImagenByte(byte[] imagenByte) {
		this.imagenByte = imagenByte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}