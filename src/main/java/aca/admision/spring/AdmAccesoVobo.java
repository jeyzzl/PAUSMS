package aca.admision.spring;

public class AdmAccesoVobo {
	private String codigoPersonal;
	private String carreraId;
	
	public AdmAccesoVobo(){
		codigoPersonal 	= "";
		carreraId		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
}