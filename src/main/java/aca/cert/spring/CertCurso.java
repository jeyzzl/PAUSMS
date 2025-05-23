/**
 * 
 */
package aca.cert.spring;

/**
 * @author elifo
 *
 */
public class CertCurso {
	 private String cursoId;
	 private String planId;
	 private String clave;
	 private String cicloId;
	 private String cursoNombre;
	 private String fst;
	 private String fsp;
	 private String creditos;
	 private String orden;
	 private String tipoCursoId;
	 private String creditos2;
	 
	 public CertCurso(){
		 cursoId		= "";
		 planId			= "";
		 clave			= "";
		 cicloId		= "";
		 cursoNombre	= "";
		 fst			= "";
		 fsp			= "";
		 creditos		= "";
		 orden			= "";
		 tipoCursoId	= "";
		 creditos2		= "0";
	 }
	
	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCicloId() {
		return cicloId;
	}

	public void setCicloId(String cicloId) {
		this.cicloId = cicloId;
	}

	public String getCursoNombre() {
		return cursoNombre;
	}

	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
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

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getTipoCursoId() {
		return tipoCursoId;
	}

	public void setTipoCursoId(String tipoCursoId) {
		this.tipoCursoId = tipoCursoId;
	}

	public String getCreditos2() {
		return creditos2;
	}

	public void setCreditos2(String creditos2) {
		this.creditos2 = creditos2;
	}

}