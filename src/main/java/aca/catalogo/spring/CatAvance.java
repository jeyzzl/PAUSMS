// Bean del Catalogo de Religiones
package  aca.catalogo.spring;

public class CatAvance{
	private String avanceId;	
	private String nombreAvance;
	
	public CatAvance(){
		avanceId 		= "";
		nombreAvance	= "";
	}

	public String getAvanceId() {
		return avanceId;
	}

	public void setAvanceId(String avanceId) {
		this.avanceId = avanceId;
	}

	public String getNombreAvance() {
		return nombreAvance;
	}

	public void setNombreAvance(String nombreAvance) {
		this.nombreAvance = nombreAvance;
	}			
}