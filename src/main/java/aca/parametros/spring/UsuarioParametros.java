package aca.parametros.spring;

public class UsuarioParametros {
	
	private String codigoPersonal;
	private String cargas;
	private String modalidades;
	private String fInicio;
	private String fFinal;	
	
	public UsuarioParametros() {
		codigoPersonal 	= "";
		cargas 			= "";
		modalidades 	= "";
		fInicio 		= "";
		fFinal 			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}
}
