//Bean de la tabla ARCH_GRUPO
package aca.archivo.spring;

public class ArchGrupoPlan{
    private String grupoId;
	private String planId;
	private String usuario;
	
	public ArchGrupoPlan(){
		grupoId 		= "0";
		planId			= "-";
		usuario		 	= "-";
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}