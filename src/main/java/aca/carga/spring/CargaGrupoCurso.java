// Bean del Catalogo de Materias de los grupos
package  aca.carga.spring;

public class CargaGrupoCurso{
	private String cursoCargaId;
	private String cursoId;
	private String origen;
	private String grupoHorario;
	
	public CargaGrupoCurso(){
		cursoCargaId	= "";
		cursoId			= "";
		origen			= "";
		grupoHorario	= "";
	}
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}
	
	public String getOrigen(){
		return origen;
	}
	
	public void setOrigen( String origen){
		this.origen = origen;
	}

	public String getGrupoHorario() {
		return grupoHorario;
	}

	public void setGrupoHorario(String grupoHorario) {
		this.grupoHorario = grupoHorario;
	}

}