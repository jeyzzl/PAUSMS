//Bean del Catalogo Cursos

package aca.plan.spring;

import java.util.ArrayList;

public class MapaOptativa {
	private String cursoId;
	private String optativaId;
	private ArrayList<String> opta;
		
	public MapaOptativa(){
		cursoId			= "";
		optativaId		= "";
		opta			= new ArrayList<String>();
	}
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}	
	
	public String getOptativaId(){
		return optativaId;
	}
	
	public void setOptativaId( String optativaId){
		this.optativaId = optativaId;
	}
	
	public void setOptativa (String opta){
		this.opta.add(opta);	
	}
	public ArrayList<String> getOptativa(){
		return opta;
	}
	
}