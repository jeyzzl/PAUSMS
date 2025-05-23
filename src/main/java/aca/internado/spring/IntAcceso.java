package aca.internado.spring;

/**
 * @author Jose
 *
 */
public class IntAcceso {
	private String codigoPersonal;
	private String dormitorioId;
	private String pasillo;
	private String rol;
	
	public IntAcceso(){
	   	dormitorioId	= "0";
	   	codigoPersonal	= "-";
	   	rol				= "A";
	   	pasillo			= "-";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getDormitorioId() {
		return dormitorioId;
	}
	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}
	public String getPasillo() {
		return pasillo;
	}
	public void setPasillo(String pasillo) {
		this.pasillo = pasillo;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}