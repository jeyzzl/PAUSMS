package aca.investiga.spring;

/**
 * @author etorres
 *
 */
public class InvReferente {
	private String codigoId;
	private String carreraId;

	public InvReferente(){
		codigoId	= "";
		carreraId	= "";
	}	

	public String getCodigoId() {
		return codigoId;
	}
	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}
	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
}