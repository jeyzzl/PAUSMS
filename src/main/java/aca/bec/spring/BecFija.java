package aca.bec.spring;

public class BecFija {
	private String idEjercicio;
	private String idCcosto;
	private String fecha;
	private String usuario;
	
	public BecFija(){
		setIdEjercicio("");
		setIdCcosto("");
		setFecha("");
		setUsuario("");
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getIdCcosto() {
		return idCcosto;
	}

	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
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
