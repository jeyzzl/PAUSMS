package aca.investiga.spring;

public class InvBitacora {
	
	private String proyectoId;
	private String folio;
	private String fecha;
	private String usuario;
	private String estadoOld;
	private String estadoNew;

	public InvBitacora(){
		proyectoId 		= "";
		folio			= "";
		fecha			= "";
		usuario			= "";
		estadoOld		= "";
		estadoNew		= "";
	}
	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
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
	
	public String getEstadoOld() {
		return estadoOld;
	}
	
	public void setEstadoOld(String estadoOld) {
		this.estadoOld = estadoOld;
	}
	
	public String getEstadoNew() {
		return estadoNew;
	}
	
	public void setEstadoNew(String estadoNew) {
		this.estadoNew = estadoNew;
	}	
	
}