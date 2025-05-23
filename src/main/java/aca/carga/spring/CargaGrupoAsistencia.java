package aca.carga.spring;

public class CargaGrupoAsistencia {

	private String cursoCargaGrupoId;
	private String folio;
	private String codigoPersonal;
	private String cursoId;
	private String estado;
	
	public CargaGrupoAsistencia() {
		cursoCargaGrupoId	= "";
		folio		 		= "";
		codigoPersonal		= "";
		cursoId				= "";
		estado				= "";
	}

	public String getCargaGrupoId() {
		return cursoCargaGrupoId;
	}

	public void setCargaGrupoId(String cargaGrupoId) {
		this.cursoCargaGrupoId = cargaGrupoId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
