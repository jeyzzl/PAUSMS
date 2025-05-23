/**
 * 
 */
package aca.hca.spring;
/**
 * @author etorres
 *
 */
public class HcaMaestro {
	private String codigoPersonal;
	private String facultadId;
	private String carreraId;
	private String estado;
	
	public HcaMaestro(){
		codigoPersonal	= "";
		facultadId		= "";
		carreraId		= "";
		estado			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFacultadId() {
		return facultadId;
	}
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}