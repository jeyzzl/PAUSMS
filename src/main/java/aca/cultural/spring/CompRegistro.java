package  aca.cultural.spring;

public class CompRegistro{
	private String eventoId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	private String usuario;
	private String facultadId;
	private String carreraId;
	private String fechaRegistro;
		
	public CompRegistro(){
		eventoId		= "";
		codigoPersonal	= "";
		fecha			= "";
		estado			= "";
		usuario			= "";
		facultadId		= "";
		carreraId		= "";
		fechaRegistro	= "";
	}	
	
	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
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
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}
	
	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}