 // Bean de datos personales del alumno 
 package  aca.graduacion.spring;
 
 public class AlumEgreso{
	 
	private String eventoId;
	private String codigoPersonal;
	private String carreraId;
 	private String planId;
 	private String avance;
 	private String titulado;
 	private String promedio;
 	private String fecha;
 	private String usuario;
 	private String diploma;
 	private String permiso;
 	private String confirmar;
 	private String comentario;
 	
 	
 	public AlumEgreso(){
 		eventoId			= "";
 		codigoPersonal		= "";
 		carreraId			= "";
 		planId				= "";
 		avance				= "";
 		titulado			= "";
 		promedio			= "";
 		fecha				= "";
 		usuario				= "";
 		diploma				= "-";
 		permiso				= "-";
 		confirmar			= "N";
 		comentario 			= "-";
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

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = avance;
	}

	public String getTitulado() {
		return titulado;
	}
	public void setTitulado(String titulado) {
		this.titulado = titulado;
	}

	public String getPromedio() {
		return promedio;
	}
	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	
	public String getPermiso() {
		return permiso;
	}
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getConfirmar() {
		return confirmar;
	}
	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
 }