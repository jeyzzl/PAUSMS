package aca.pg.archivo.spring;

public class PosFotoAlum {
	private String codigoPersonal;
	private String tipo;
	private byte[] foto;	
	private String fecha;
	private String usuario;
	private String rechazada;
	private String comentario;
	
	public PosFotoAlum(){
		codigoPersonal	= "";
		tipo			= "O";
		foto			= null;		
		fecha 			= "";
		usuario 		= "-";
		rechazada 		= "N";
		comentario 		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
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

	public String getRechazada() {
		return rechazada;
	}

	public void setRechazada(String rechazada) {
		this.rechazada = rechazada;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}