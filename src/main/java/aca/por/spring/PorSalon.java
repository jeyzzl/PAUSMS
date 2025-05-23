// Bean del Catalogo Curso
package  aca.por.spring;

public class PorSalon{
	
	private String salonId;
	private String salonNombre;
	
	public PorSalon(){
		salonId			= "";
		salonNombre		= "";		
	}

	public String getSalonId() {
		return salonId;
	}

	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	public String getSalonNombre() {
		return salonNombre;
	}

	public void setSalonNombre(String salonNombre) {
		this.salonNombre = salonNombre;
	}
	
}