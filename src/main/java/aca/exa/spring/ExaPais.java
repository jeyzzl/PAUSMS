// Bean del Catalogo de Paises
package  aca.exa.spring;

public class ExaPais{
	private String paisId;	
	private String paisNombre;
	
	public ExaPais(){
		paisId 			= "";
		paisNombre 		= "";
	}
	
	public String getPaisId(){
		return paisId;
	}	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}

	public String getPaisNombre() {
		return paisNombre;
	}
	public void setPaisNombre(String paisNombre) {
		this.paisNombre = paisNombre;
	}
	
}