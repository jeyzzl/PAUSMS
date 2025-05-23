package aca.carga.spring;

public class CargaGrupoEje{
	private String cursoCargaId;
	private String ejeId;	
	private String descripcion;
	
	public CargaGrupoEje(){
		cursoCargaId = "";
		ejeId 		 = "";
		descripcion  = "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEjeId() {
		return ejeId;
	}

	public void setEjeId(String ejeId) {
		this.ejeId = ejeId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}