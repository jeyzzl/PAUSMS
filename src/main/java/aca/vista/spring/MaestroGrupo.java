// Beans para la vista Maestro_Grupos

package aca.vista.spring;

public class MaestroGrupo {

	private String maestroCarrera;
	private String codigoPersonal;
	private String carreraId;
	private String numGrupos;
		
	public MaestroGrupo(){
		maestroCarrera	= "";
		codigoPersonal	= "";
		carreraId		= "";
		numGrupos		= "";
	}
	
		
	public String getMaestroCarrera() {
		return maestroCarrera;
	}


	public void setMaestroCarrera(String maestroCarrera) {
		this.maestroCarrera = maestroCarrera;
	}


	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCarreraId() {
		return carreraId;
	}


	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}


	public String getNumGrupos() {
		return numGrupos;
	}


	public void setNumGrupos(String numGrupos) {
		this.numGrupos = numGrupos;
	}
}