/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

public class TitCarreraOr {
	
    String cveInstitucion;
    String nombreInstitucion;
    String tipoSostenimiento;
    String tipoEducativo;
    String nivelEstudios;
    String cveCarrera;
    String carrera;

    public TitCarreraOr() {
    	
    	cveInstitucion 	 	= "";
    	nombreInstitucion	= "";
    	tipoSostenimiento	= "";
    	tipoEducativo		= "";
    	nivelEstudios		= "";
    	cveCarrera			= "";
    	carrera				= "";
    }

	public String getCveInstitucion() {
		return cveInstitucion;
	}

	public void setCveInstitucion(String cveInstitucion) {
		this.cveInstitucion = cveInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getTipoSostenimiento() {
		return tipoSostenimiento;
	}

	public void setTipoSostenimiento(String tipoSostenimiento) {
		this.tipoSostenimiento = tipoSostenimiento;
	}

	public String getTipoEducativo() {
		return tipoEducativo;
	}

	public void setTipoEducativo(String tipoEducativo) {
		this.tipoEducativo = tipoEducativo;
	}

	public String getNivelEstudios() {
		return nivelEstudios;
	}

	public void setNivelEstudios(String nivelEstudios) {
		this.nivelEstudios = nivelEstudios;
	}

	public String getCveCarrera() {
		return cveCarrera;
	}

	public void setCveCarrera(String cveCarrera) {
		this.cveCarrera = cveCarrera;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

}