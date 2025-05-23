package aca.musica.spring;

public class MusiAutoriza {
	
	private String codigoPersonal;
 	private String cargaId;
 	private String fecha;
 	private String usuario;
 	
 	public MusiAutoriza() {
 		codigoPersonal 	= "";
 		cargaId 		= "";
 		fecha	 		= aca.util.Fecha.getHoy();
 		usuario			= "0";
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
 	
}
