package aca.pg.archivo.spring;

public class PosArchGeneral {
	private String matricula;
	private String folio;
	private String fecha;
	private String usuario;
	private int imagen;
	private byte[] imagenByte;
	
	public PosArchGeneral(){
		matricula		= "";
		folio 			= "0";		
		fecha			= "";
		usuario			= "";
		imagen			= 0;
		imagenByte 		= null;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getImagen() {
		return imagen;
	}
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}

	public byte[] getImagenByte() {
		return imagenByte;
	}
	public void setImagenByte(byte[] imagenByte) {
		this.imagenByte = imagenByte;
	}	
}