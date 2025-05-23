package aca.financiero.spring;

public class ContCcosto {
	private String idEjercicio;
	private String idCcosto;	
	private String nombre;
	private String detalle;
	private String iniciales;
	private String rfc;
	
	// Constructor
	public ContCcosto(){		
		idEjercicio		= "";
		idCcosto		= "";
		nombre			= "";
		detalle			= "";
		iniciales		= "";
		rfc				= "";
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
}