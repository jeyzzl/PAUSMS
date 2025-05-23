package aca.apFisica.spring;

public class ApFisicaAlumno{	
	private String grupoId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	private String cursoCargaId;
	private String carreraId;
	
	// Constructor
	public ApFisicaAlumno(){
		grupoId			= "0";
		codigoPersonal	= "0";
		fecha			= "";
		estado			= "A";
		cursoCargaId	= "0";
		carreraId		= "-";
	}
	
	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}
	
}