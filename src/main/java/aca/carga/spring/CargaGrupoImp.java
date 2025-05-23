// Bean de Carga_Grupo_Imp
package  aca.carga.spring;

public class CargaGrupoImp{
	private String grupoId;
	private String cursoId;
	private String maestro;
	private String alumnos;
	private String cursoCargaId;
	private String fecha;
	private String grupo;
	
	public CargaGrupoImp(){
		grupoId			= "";
		cursoId			= "";
		maestro			= "";
		alumnos			= "";
		cursoCargaId	= "";
		fecha			= "";
		grupo			= "";		
	}	
	
	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
}