package aca.bec.spring;

public class BecPeriodo{
	
	private String periodoId			= "";
	private String periodoNombre		= "";
	private String fechaIni				= "";
	private String fechaFin				= "";
	private String estado				= "";
	private String idEjercicio			= "";
	private String tipo					= "";
	private String fechasPrepa			= "";
	private String fechasUniversidad 	= "";
	private String fechasPeriodo	 	= "";
	
	public BecPeriodo(){
		periodoId			= "";
		periodoNombre		= "";
		fechaIni			= "";
		fechaFin			= "";
		estado				= "";
		idEjercicio			= "";
		tipo				= "";
		fechasPrepa			= "";
		fechasUniversidad	= "";
		fechasPeriodo		= "";
	}
	
	public String getFechasPeriodo() {
		return fechasPeriodo;
	}

	public void setFechasPeriodo(String fechasPeriodo) {
		this.fechasPeriodo = fechasPeriodo;
	}

	public String getFechasPrepa() {
		return fechasPrepa;
	}

	public void setFechasPrepa(String fechasPrepa) {
		this.fechasPrepa = fechasPrepa;
	}

	public String getFechasUniversidad() {
		return fechasUniversidad;
	}

	public void setFechasUniversidad(String fechasUniversidad) {
		this.fechasUniversidad = fechasUniversidad;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}