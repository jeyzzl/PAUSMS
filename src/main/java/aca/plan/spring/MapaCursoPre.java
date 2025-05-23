// Bean del Catalogo Cursos prerrequisito
package  aca.plan.spring;

public class MapaCursoPre{
	
	private String cursoId;
	private String cursoIdPre;
	
	public MapaCursoPre(){
		cursoId		= "0";		
		cursoIdPre 	= "0";				
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}
	
	public String getCursoIdPre(){
		return cursoIdPre;
	}
	
	public void setCursoIdPre( String cursoIdPre){
		this.cursoIdPre = cursoIdPre;
	}	
}