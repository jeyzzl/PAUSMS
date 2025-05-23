package aca.bec.spring;

public class BecInforme {
	
	private String informeId;
	private String informeNombre;
	private String fechaIni;
	private String fechaFin;
	private String nivel;
	private String orden;
	private String version;
	private String estado;
	private String idEjercicio;
	
	public BecInforme(){
		informeId			= "0";
		informeNombre		= "-";
		fechaIni			= aca.util.Fecha.getHoy();
		fechaFin			= aca.util.Fecha.getHoy();
		nivel				= "0";
		orden 				= "0";
		version				= "0";
		estado 				= "A";
		idEjercicio			= "0";
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getInformeId() {
		return informeId;
	}
	
	public void setInformeId(String informeId) {
		this.informeId = informeId;
	}

	public String getInformeNombre() {
		return informeNombre;
	}

	public void setInformeNombre(String informeNombre) {
		this.informeNombre = informeNombre;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}	
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}
	
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

}
