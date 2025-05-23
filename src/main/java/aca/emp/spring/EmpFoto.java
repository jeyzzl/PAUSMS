package aca.emp.spring;

public class EmpFoto {

	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String usuario;
	private byte[] foto;	
	
	public EmpFoto(){
		codigoPersonal	= "";
		folio 			= "";
		fecha 			= "";
		usuario 			= "";
		foto			= null;	
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
}
