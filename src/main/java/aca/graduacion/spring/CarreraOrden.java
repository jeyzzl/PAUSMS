 // Bean de orden de la carrera
 package  aca.graduacion.spring;
 
 public class CarreraOrden{
	 
 	private String carreraId;
 	private String orden; 	
 	
 	public CarreraOrden(){
 		carreraId		= "";
 		orden			= ""; 		
 	}

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	} 	
 }