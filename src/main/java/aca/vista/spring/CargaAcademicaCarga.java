package  aca.vista.spring;

public class CargaAcademicaCarga{
	private String planId;
	private String carreraId;
	private String ciclo;	
	private String cursoId;	
	private String cursoCargaId;
	private String nombreCurso;
	private String grupo;
	private String letra;
	private String profesor;
	private String carrera;
	private String lCarrera;
	private String horas;
	private String creditos;
	private String estado;	
	private String modalidad;
	private String bloque;
	private String optativa;
	private String tieneHorario;
	
	public CargaAcademicaCarga(){
		planId			= "";
		carreraId		= "";
		ciclo			= "";
		cursoId			= "";
		cursoCargaId	= "";
		nombreCurso		= "";
		grupo			= "";
		letra			= "";
		profesor		= "";
		carrera			= "";
		lCarrera		= "";
		horas			= "";
		estado			= "";
		modalidad		= "";	
		bloque			= "";
		optativa		= "";
		tieneHorario	= "no";
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getlCarrera() {
		return lCarrera;
	}

	public void setlCarrera(String lCarrera) {
		this.lCarrera = lCarrera;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getOptativa() {
		return optativa;
	}

	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}

	public String getTieneHorario() {
		return tieneHorario;
	}

	public void setTieneHorario(String tieneHorario) {
		this.tieneHorario = tieneHorario;
	}
}