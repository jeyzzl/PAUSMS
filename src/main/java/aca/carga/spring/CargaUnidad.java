package aca.carga.spring;

public class CargaUnidad {
	private String cursoCargaId;
	private String unidadId;	
	private String unidadNombre;
	private String orden;
	
	public CargaUnidad(){
		cursoCargaId = "";
		unidadId	 = "";
		unidadNombre = "";
		orden        = "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
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

	public String getUnidadNombre() {
		return unidadNombre;
	}

	public void setUnidadNombre(String unidadNombre) {
		this.unidadNombre = unidadNombre;
	}
	
}