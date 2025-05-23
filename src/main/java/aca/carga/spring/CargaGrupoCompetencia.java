package aca.carga.spring;

public class CargaGrupoCompetencia {
	private String cursoCargaId;
	private String competenciaId;	
	private String descripcion;
	
	public CargaGrupoCompetencia(){
		cursoCargaId    = "";
		competenciaId   = "";
		descripcion     = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCompetenciaId() {
		return competenciaId;
	}

	public void setCompetenciaId(String competenciaId) {
		this.competenciaId = competenciaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}