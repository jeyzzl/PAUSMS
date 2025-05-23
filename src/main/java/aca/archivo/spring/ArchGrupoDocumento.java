//Bean de la tabla ARCH_GRUPO
package aca.archivo.spring;

public class ArchGrupoDocumento {
    private String grupoId;
	private String documentoId;	
	
	public ArchGrupoDocumento(){
		grupoId 		= "0";
		documentoId		= "-";		
	}
	
	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}
	
}