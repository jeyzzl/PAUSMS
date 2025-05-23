package aca.carga.spring;

public class CargaUnidadComp {
	private String cursoCargaId;
	private String unidadId;	
	private String competenciaId;
	
	public CargaUnidadComp(){
		cursoCargaId   = "";
		unidadId 	   = "";
		competenciaId  = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}
	
}