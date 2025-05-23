//Bean del Catalogo Cargas
package  aca.carga.spring;

public class CargaFinanciero{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String comentario;	
	private String status;
	private String usuario;
	private String fecha;
	
	public CargaFinanciero(){
		codigoPersonal	= "0";
		cargaId			= "0";
		bloqueId		= "0";
		comentario		= "";
		status			= "I";
		usuario			= "0";
		fecha			= "";	
		
	}
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getBloqueId() {
		return bloqueId;
	}
	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
}
