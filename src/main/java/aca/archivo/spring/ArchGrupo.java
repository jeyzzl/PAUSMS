//Bean de la tabla ARCH_GRUPO
package aca.archivo.spring;

public class ArchGrupo {
    private String grupoId;
	private String grupoNombre;
	private String comentario;
	
	public ArchGrupo(){
		grupoId 		= "0";
		grupoNombre		= "-";
		comentario	 	= "-";
	}
	
	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}