package aca.carga.spring;

public class CargaGrupo {
	private String cursoCargaId;
	private String cargaId;
	private String bloqueId;
	private String carreraId;
	private String codigoPersonal;
	private String grupo;
	private String modalidadId;
	private String fInicio;
	private String fFinal;
	private String fEntrega;
	private String restriccion;
	private String estado;
	private String horario;
	private String fEvaluacion;
	private String valeucas;
	private String numAlum;
	private String semanas;
	private String optativa;
	private String comentario;
	private String codigoOtro;
	private String precio;
	private String modo;
	private String fecha;
	private String usuario;
	
	public CargaGrupo(){
		cursoCargaId		= "";
		cargaId				= "";
		bloqueId			= "0";
		carreraId			= "0";
		codigoPersonal		= "0";
		grupo				= "U";
		modalidadId			= "1";
		fInicio				= "";
		fFinal				= "";
		fEntrega			= "";
		restriccion			= "";
		estado				= "0";
		horario				= "";
		fEvaluacion			= "";
		valeucas			= "";
		numAlum				= "0";
		semanas				= "16";
		optativa			= "";
		comentario 			= "-";
		codigoOtro			= "0";
		precio 				= "0";
		modo 				= "P";
		fecha 				= "";
		usuario				= "0";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getfInicio() {
		return fInicio;
	}
	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}
	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getfEntrega() {
		return fEntrega;
	}
	public void setfEntrega(String fEntrega) {
		this.fEntrega = fEntrega;
	}

	public String getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getfEvaluacion() {
		return fEvaluacion;
	}
	public void setfEvaluacion(String fEvaluacion) {
		this.fEvaluacion = fEvaluacion;
	}

	public String getValeucas() {
		return valeucas;
	}
	public void setValeucas(String valeucas) {
		this.valeucas = valeucas;
	}

	public String getNumAlum() {
		return numAlum;
	}
	public void setNumAlum(String numAlum) {
		this.numAlum = numAlum;
	}

	public String getSemanas() {
		return semanas;
	}
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String getOptativa() {
		return optativa;
	}
	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getCodigoOtro() {
		return codigoOtro;
	}
	public void setCodigoOtro(String codigoOtro) {
		this.codigoOtro = codigoOtro;
	}

	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
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
	
}
