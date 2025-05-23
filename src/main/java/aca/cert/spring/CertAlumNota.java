/**
 * 
 */
package aca.cert.spring;

/**
 * @author Jose Torres
 *
 */
/**
 * @author JazerTorres
 *
 */
public class CertAlumNota {
	private String codigoPersonal;
	private String folio;
	private String planId;
	private String cicloId;
	private String cursoId;
	private String curso;
	private String estado;
	private String nota;
	private String notaLetra;
	private String optativa;
	private String promedia;
	
	
	public CertAlumNota(){
		codigoPersonal	= "";
		folio			= "";
		planId			= "";
		cicloId			= "";
		cursoId			= "";
		curso			= "";
		estado			= "";
		nota			= "";
		notaLetra		= "";
		optativa		= "";
		promedia		= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCicloId() {
		return cicloId;
	}

	public void setCicloId(String cicloId) {
		this.cicloId = cicloId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getNotaLetra() {
		return notaLetra;
	}

	public void setNotaLetra(String notaLetra) {
		this.notaLetra = notaLetra;
	}

	public String getOptativa() {
		return optativa;
	}

	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}

	public String getPromedia() {
		return promedia;
	}

	public void setPromedia(String promedia) {
		this.promedia = promedia;
	}
	
}