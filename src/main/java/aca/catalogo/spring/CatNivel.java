// Bean del Catalogo de Niveles
package  aca.catalogo.spring;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.relational.core.mapping.Table;

//@Table("CAT_NIVEL")
public class CatNivel{
	//@Id
	private String nivelId;
	private String nombreNivel;
	private String orden;
	private String estado;
		
	public CatNivel(){
		nivelId		= "";
		nombreNivel	= "-";
		orden		= "0";
		estado		= "A";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNivelId(){
		return nivelId;
	}
	
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}	
	
	public String getNombreNivel(){
		return nombreNivel;
	}
	
	public void setNombreNivel( String nombreNivel){
		this.nombreNivel = nombreNivel;
	}			
	
}