// Bean de Usuarios con Permiso en Edificio
package  aca.catalogo.spring;

public class CatEdificioUsuario{
	private String edificioId;	
	private String codigo_personal;

	public CatEdificioUsuario(){
		edificioId 		= "0";		
		codigo_personal	= "0";
	}

	public String getEdificioId(){
		return edificioId;
	}	
	public void setEdificioId( String edificioId){
		this.edificioId = edificioId;
	}
	
	public String getCodigo_personal() {
		return codigo_personal;
	}
	public void setCodigo_personal(String codigo_personal) {
		this.codigo_personal = codigo_personal;
	}
}