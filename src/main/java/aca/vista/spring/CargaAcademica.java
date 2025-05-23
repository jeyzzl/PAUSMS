// Clase para la vista CARGA_ACADEMICA
package  aca.vista.spring;

public class CargaAcademica{
	private String cursoCargaId;
	private String cargaId;
	private String bloqueId;
	private String codigoPersonal;
	private String nombre;
	private String carreraId;
	private String grupo;
	private String modalidadId;
	private String estado;
	private String fInicio;
	private String fFinal;	
	private String fEntrega;
	private String origen;	
	private String planId;
	private String cursoId;
	private String nombreCurso;
	private String ciclo;
	private String creditos;
	private String ht;
	private String hp;
	private String hi;
	private String notaAprobatoria;	
	private String valeucas;
	private String numAlum;
	private String semanas;
	private String optativa;
	private String horario;	
	private String grupoHorario;
	private String hh;
	private String salon;
	private String modo;
	
	public CargaAcademica(){
		cursoCargaId	= "";
		cargaId			= "";
		bloqueId		= "";
		codigoPersonal	= "";
		nombre			= "";
		carreraId		= "";
		grupo			= "";
		modalidadId		= "";
		estado			= "";
		fInicio			= "";
		fFinal			= "";
		fEntrega		= "";
		origen			= "";	
		planId			= "";
		cursoId			= "";
		nombreCurso		= "";
		ciclo			= "";
		creditos		= "";
		ht				= "";
		hp				= "";
		hi				= "";
		notaAprobatoria	= "";		
		valeucas		= "";
		numAlum			= "";
		semanas			= "";
		optativa 		= "";
		horario 		= "S";
		grupoHorario	= "XX";
		salon			= "S";
		salon			= "";
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

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getHt() {
		return ht;
	}

	public void setHt(String ht) {
		this.ht = ht;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getNotaAprobatoria() {
		return notaAprobatoria;
	}

	public void setNotaAprobatoria(String notaAprobatoria) {
		this.notaAprobatoria = notaAprobatoria;
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

	public String getHi() {
		return hi;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getGrupoHorario() {
		return grupoHorario;
	}

	public void setGrupoHorario(String grupoHorario) {
		this.grupoHorario = grupoHorario;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getSalon() {
		return salon;
	}
	public void setSalon(String salon) {
		this.salon = salon;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}	
}