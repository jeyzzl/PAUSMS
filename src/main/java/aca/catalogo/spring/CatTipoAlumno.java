// Bean del Catalogo tipo de alumno
package  aca.catalogo.spring;

public class CatTipoAlumno{
	private String tipoId;	
	private String nombreTipo;
	
	public CatTipoAlumno(){
		tipoId 		= "";
		nombreTipo	= "";
	}
	
	public String getTipoId(){
		return tipoId;
	}
	
	public void setTipoId( String tipoId){
		this.tipoId = tipoId;
	}
	
	public String getNombreTipo(){
		return nombreTipo;
	}
	
	public void setNombreTipo( String nombreTipo){
		this.nombreTipo = nombreTipo;
	}
	
}