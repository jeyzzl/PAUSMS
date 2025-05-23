package aca.catalogo.spring;


public class CatInstrumento {
	private String instrumentoId;	
	private String descripcion;
	
	public CatInstrumento(){
		instrumentoId     = "";
		descripcion     = "";
	}	

	public String getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
