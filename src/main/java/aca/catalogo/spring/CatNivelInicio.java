// Bean del Catalogo de Religiones
package  aca.catalogo.spring;

public class CatNivelInicio{
	private String nivelInicioId;	
	private String nombreNivel;
	private String nombreCorto;
	
	public CatNivelInicio(){
		nivelInicioId 			= "0";
		nombreNivel 		= "-";
		nombreCorto 		= "-";
	}
	

	
	public String getNivelInicioId() {
		return nivelInicioId;
	}
	public void setNivelInicioId(String nivelInicioId) {
		this.nivelInicioId = nivelInicioId;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}
	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
}