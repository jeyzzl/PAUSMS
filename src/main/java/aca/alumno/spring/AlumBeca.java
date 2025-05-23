package aca.alumno.spring;


public class AlumBeca {
	private String codigoPersonal;
	private String beca;
	
	public AlumBeca(){
		codigoPersonal	= "";
		beca			= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getBeca() {
		return beca;
	}
	public void setBeca(String beca) {
		this.beca = beca;
	}
}
