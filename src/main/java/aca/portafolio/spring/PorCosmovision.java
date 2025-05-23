package aca.portafolio.spring;

public class PorCosmovision {
	
	private String codigoPersonal;
	private String periodoId;
	private String filosofia;
	private String mision;
	private String vision;
	private String valores;
	private String reflexion;
	
	public PorCosmovision(){
		codigoPersonal 	= "";
		periodoId 		= "";
		filosofia 		= "";
		mision 			= "";
		vision 			= "";
		valores 		= "";
		reflexion 		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFilosofia() {
		return filosofia;
	}

	public void setFilosofia(String filosofia) {
		this.filosofia = filosofia;
	}

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getValores() {
		return valores;
	}

	public void setValores(String valores) {
		this.valores = valores;
	}

	public String getReflexion() {
		return reflexion;
	}

	public void setReflexion(String reflexion) {
		this.reflexion = reflexion;
	}	
}
