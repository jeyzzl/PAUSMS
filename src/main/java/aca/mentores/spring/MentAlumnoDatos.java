package aca.mentores.spring;

public class MentAlumnoDatos {
	private String codigoPersonal;
	private String iglesia;
	private String claseEs;
	
	public MentAlumnoDatos(){
		codigoPersonal	= "";
		iglesia			= "";
		claseEs			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getIglesia() {
		return iglesia;
	}

	public void setIglesia(String iglesia) {
		this.iglesia = iglesia;
	}

	public String getClaseEs() {
		return claseEs;
	}

	public void setClaseEs(String claseEs) {
		this.claseEs = claseEs;
	}
	
}