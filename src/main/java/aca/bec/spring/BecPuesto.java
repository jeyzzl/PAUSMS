package aca.bec.spring;

public class BecPuesto {
	private String idEjercicio;
	private String idCcosto;
	private String categoriaId;
	private String justificacion;
	private String funcion;
	private String competencias;
	private String certificaciones;
	private String cantidad;
	private String estado;
	private String otrasComp;
	private String periodoId;
	
	public BecPuesto(){
		idEjercicio		= "";
		idCcosto		= "";
		categoriaId		= "";
		justificacion	= "";
		funcion			= "";
		competencias	= "";
		certificaciones	= "";
		cantidad		= "";
		estado			= "";
		otrasComp		= "";
		periodoId		= "";
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

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getCompetencias() {
		return competencias;
	}

	public void setCompetencias(String competencias) {
		this.competencias = competencias;
	}

	public String getCertificaciones() {
		return certificaciones;
	}

	public void setCertificaciones(String certificaciones) {
		this.certificaciones = certificaciones;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getOtrasComp() {
		return otrasComp;
	}

	public void setOtrasComp(String otrasComp) {
		this.otrasComp = otrasComp;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	
}