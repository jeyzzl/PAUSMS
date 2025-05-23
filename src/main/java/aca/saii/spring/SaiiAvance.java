package aca.saii.spring;

public class SaiiAvance {
	
	private String grupoId	= "";
	private int porcentaje	= 0;	
	
	public SaiiAvance(){		
		grupoId		= "0";
		porcentaje	= 0;
		
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	
}