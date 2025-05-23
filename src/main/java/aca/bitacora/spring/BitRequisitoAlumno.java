package aca.bitacora.spring;

public class BitRequisitoAlumno {
	
	private String codigoPersonal;
	private String tramiteId;
	private String requisitoId;
	private String fecha;
	private String codigoEmpleado;
	private String estado;
	private String folio;
	
	public BitRequisitoAlumno(){
		codigoPersonal	= "0";
		tramiteId 		= "0";
		requisitoId 	= "0";
		fecha 			= "";
		codigoEmpleado	= "0";
		estado			= "A";
		folio			= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}
