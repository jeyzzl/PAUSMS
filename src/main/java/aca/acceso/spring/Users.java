package aca.acceso.spring;

public class Users {	
	
	private String id;
	private String usuario;
	private String claveMd5;
	private String claveSh1;
	private String estado;
	
	public Users(){
		id			= "0";
		claveMd5	= "";
		claveSh1	= "";
		estado 		= ""; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClaveMd5() {
		return claveMd5;
	}

	public void setClaveMd5(String claveMd5) {
		this.claveMd5 = claveMd5;
	}

	public String getClaveSh1() {
		return claveSh1;
	}

	public void setClaveSh1(String claveSh1) {
		this.claveSh1 = claveSh1;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}