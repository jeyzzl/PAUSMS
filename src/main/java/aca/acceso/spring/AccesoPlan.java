package aca.acceso.spring;

public class AccesoPlan{
	
	private String codigoPersonal;
	private String planId;
	private String carreraId;
	private String fecha;
	private String usuario;
	
	public AccesoPlan(){
		codigoPersonal	= "0";
		planId			= "0";
		carreraId		= "0";
		fecha			= null;
		usuario 		= "9800308";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
}