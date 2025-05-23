/**
 * 
 */
package aca.cert.spring;

/**
 * @author elifo
 *
 */
public class CertPlan {
	private String planId;
	private String facultad;
	private String carrera;
	private String numCursos;
	private String semanas;
	private String tInicial;
	private String tFinal;
	private String nota;
	private String pie;
	private String clave;
	private String fst;
	private String fsp;
	private String componente;
	private String curso;
	private String rvoe;
	private String fechaRetro;
	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String creditos;
	
	public CertPlan(){
		planId		= "";
		facultad	= "";
		carrera		= "";
		numCursos	= "";
		semanas		= "";
		tInicial	= "";
		tFinal		= "";
		nota		= "";
		pie			= "";
		clave		= "";
		fst			= "";
		fsp			= "";
		componente	= "";
		curso		= "";
		rvoe		= "";
		fechaRetro	= "";
		titulo1		= "";
		titulo2		= "";
		titulo3		= "";
		creditos	= "0";
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getNumCursos() {
		return numCursos;
	}

	public void setNumCursos(String numCursos) {
		this.numCursos = numCursos;
	}

	public String getSemanas() {
		return semanas;
	}

	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String gettInicial() {
		return tInicial;
	}

	public void settInicial(String tInicial) {
		this.tInicial = tInicial;
	}

	public String gettFinal() {
		return tFinal;
	}

	public void settFinal(String tFinal) {
		this.tFinal = tFinal;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getPie() {
		return pie;
	}

	public void setPie(String pie) {
		this.pie = pie;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getFst() {
		return fst;
	}

	public void setFst(String fst) {
		this.fst = fst;
	}

	public String getFsp() {
		return fsp;
	}

	public void setFsp(String fsp) {
		this.fsp = fsp;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getRvoe() {
		return rvoe;
	}

	public void setRvoe(String rvoe) {
		this.rvoe = rvoe;
	}

	public String getFechaRetro() {
		return fechaRetro;
	}

	public void setFechaRetro(String fechaRetro) {
		this.fechaRetro = fechaRetro;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	
}