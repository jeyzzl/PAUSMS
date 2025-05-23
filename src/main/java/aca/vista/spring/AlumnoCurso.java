// Clase para la vista ALUMNO_CURSO
package  aca.vista.spring;

public class AlumnoCurso{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String cursoCargaId;
	private String carreraId;
	private	String modalidadId;
	private String planId;
	private String cursoId;
	private String nombreCurso;
	private String ciclo;	
	private String creditos;
	private String ht;	
	private String hp;
	private String notaAprobatoria;
	private String cursoId2;
	private String nombreCurso2;
	private String creditos2;
	private String ht2;
	private String hp2;
	private String nota;
	private String fEvaluacion;
	private String tipoCalId;
	private String notaExtra;
	private String fExtra;
	private String convalidacion;
	private String titulo;
	private String fTitulo;
	private String grupo;
	private String maestro;
	private String estado;	
	private String correccion;
	private String optativa;
	private String notaConva;
	
	public AlumnoCurso(){
		codigoPersonal	= "0";
		cargaId			= "0";
		bloqueId		= "0";
		cursoCargaId	= "0";
		carreraId		= "0";
		modalidadId		= "1";
		planId			= "0";
		cursoId			= "0";
		nombreCurso		= "-";
		ciclo			= "0";
		creditos		= "0";
		ht				= "0";
		hp				= "0";
		notaAprobatoria	= "0";
		cursoId2		= "0";
		nombreCurso2	= "-";
		creditos2		= "0";
		ht2				= "0";
		hp2				= "0";
		nota			= "0";
		fEvaluacion		= "";
		tipoCalId		= "0";
		notaExtra		= "0";
		fExtra			= "";
		convalidacion	= "N";
		titulo			= "-";
		fTitulo			= "";
		grupo			= "XX";
		maestro			= "0";
		estado			= "1";		
		correccion		= "N";
		optativa		= "N";
		notaConva 		= ""; 
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

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
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

	public String getCursoId2() {
		return cursoId2;
	}

	public void setCursoId2(String cursoId2) {
		this.cursoId2 = cursoId2;
	}

	public String getNombreCurso2() {
		return nombreCurso2;
	}

	public void setNombreCurso2(String nombreCurso2) {
		this.nombreCurso2 = nombreCurso2;
	}

	public String getCreditos2() {
		return creditos2;
	}

	public void setCreditos2(String creditos2) {
		this.creditos2 = creditos2;
	}

	public String getHt2() {
		return ht2;
	}

	public void setHt2(String ht2) {
		this.ht2 = ht2;
	}

	public String getHp2() {
		return hp2;
	}

	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getFEvaluacion() {
		return fEvaluacion;
	}

	public void setFEvaluacion(String fEvaluacion) {
		this.fEvaluacion = fEvaluacion;
	}

	public String getTipoCalId() {
		return tipoCalId;
	}

	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}

	public String getNotaExtra() {
		return notaExtra;
	}

	public void setNotaExtra(String notaExtra) {
		this.notaExtra = notaExtra;
	}

	public String getFExtra() {
		return fExtra;
	}

	public void setFExtra(String fExtra) {
		this.fExtra = fExtra;
	}

	public String getConvalidacion() {
		return convalidacion;
	}

	public void setConvalidacion(String convalidacion) {
		this.convalidacion = convalidacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFTitulo() {
		return fTitulo;
	}

	public void setFTitulo(String fTitulo) {
		this.fTitulo = fTitulo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCorreccion() {
		return correccion;
	}

	public void setCorreccion(String correccion) {
		this.correccion = correccion;
	}

	public String getOptativa() {
		return optativa;
	}

	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}

	public String getNotaConva() {
		return notaConva;
	}

	public void setNotaConva(String notaConva) {
		this.notaConva = notaConva;
	}

}