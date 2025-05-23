// Bean del Catalogo de Extensiones
package  aca.catalogo.spring;

public class CatInstitucion{
	private String institucionId;
	private String nombreInstitucion;
		
	public CatInstitucion(){
		institucionId		= "";
		nombreInstitucion	= "";	
	}
	
	public String getInstitucionId(){
		return institucionId;
	}
	
	public void setInstitucionId( String institucionId){
		this.institucionId = institucionId;
	}	
	
	public String getNombreInstitucion(){
		return nombreInstitucion;
	}
	
	public void setNombreInstitucion( String nombreInstitucion){
		this.nombreInstitucion = nombreInstitucion;
	}	
}