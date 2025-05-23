package aca.bec.spring;


public class BecTipoCarrera {
	private String idEjercicio;
	private String tipo;
	private String carreraId;	
	
	public BecTipoCarrera(){
		idEjercicio			= "0";
		tipo				= "0";
		carreraId			= "00000";		
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}	
}