package  aca.exa.spring;

public class ExaDireccion{
	private String direccionId;	
	private String matricula;
	private String ciudad;
	private String direccion;	
	private String estadoId;
	private String paisId;
	private String cp;
	private String fechaActualizacion;
	private String eliminado;
	private String idDireccion;
	
	public ExaDireccion(){
		direccionId 		= "";
		matricula			= "";
		ciudad 				= "";
		direccion			= "";
		estadoId			= "";
		paisId 				= "";
		cp					= "";
		fechaActualizacion	= "";
		eliminado			= "";
		idDireccion			= "";
	}

	public String getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(String direccionId) {
		this.direccionId = direccionId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	
}