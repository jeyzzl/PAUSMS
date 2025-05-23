package aca.matricula.spring;

public class CambioCarrera {
	
	private String solicitudId;
	private String codigoPersonal;
	private String fecha;
	private String carreraBaja;
	private String carreraAlta;
	private String usuario;
	
	public CambioCarrera() {
		solicitudId 	= "";
		codigoPersonal 	= "";
		fecha 			= "";
		carreraBaja 	= "";
		carreraAlta 	= "";
		usuario 		= "";
	}

	public String getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCarreraBaja() {
		return carreraBaja;
	}

	public void setCarreraBaja(String carreraBaja) {
		this.carreraBaja = carreraBaja;
	}

	public String getCarreraAlta() {
		return carreraAlta;
	}

	public void setCarreraAlta(String carreraAlta) {
		this.carreraAlta = carreraAlta;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
