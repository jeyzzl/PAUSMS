package aca.bec.spring;

public class BecCompetencia {
	private String competenciaId;
	private String competenciaNombre;
	
	public BecCompetencia(){
		competenciaId			= "";
		competenciaNombre		= "";
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}
	
	public String getCompetenciaNombre() {
		return competenciaNombre;
	}
	
	public void setCompetenciaNombre(String competenciaNombre) {
		this.competenciaNombre = competenciaNombre;
	}	
}