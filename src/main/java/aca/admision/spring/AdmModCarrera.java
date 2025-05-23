package  aca.admision.spring;

public class AdmModCarrera{
	private String modalidadId;
	private String carreraId;
	
	public AdmModCarrera(){
		modalidadId = "0";
		carreraId 	= "0";
	}

	public String getModalidadId(){
		return modalidadId;
	}

	public void setModalidadId(String modalidadId){
		this.modalidadId = modalidadId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId){
		this.carreraId = carreraId;
	}
}