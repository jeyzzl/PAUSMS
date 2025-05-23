package aca.acceso.spring;

public class AccesoConfirmar{
	
	private String codigoPersonal;
	private String modalidadId;
	
	public AccesoConfirmar(){
		codigoPersonal	= "";
		modalidadId		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}
}