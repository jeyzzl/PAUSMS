// Bean de Estado del alumno en el procesos de matrícula( Por cada bloque).
package  aca.alumno.spring;

public class AlumEnLinea{
	
	private String codigoPersonal;
	private String cargaId;
	private String usuario;
	private String solicitud;
	private String comentarios;
	private String fecha;
	private String residenciaId;
	private String planId;
	private String coordinador;
	private String estado;
	
	public AlumEnLinea(){
		codigoPersonal		= "";
		cargaId				= "";
		usuario				= "0";
		solicitud			= "0";
		solicitud			= "0";
		comentarios			= "";
		fecha				= "";
		planId				= "";
		residenciaId		= "";
		residenciaId		= "";
		planId				= "0";
		coordinador 		= "0";
		estado 				= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getResidenciaId() {
		return residenciaId;
	}

	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(String coordinador) {
		this.coordinador = coordinador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}