package aca.carga.spring;

public class CargaUnidadTema {
	private String cursoCargaId;
	private String temaId;	
	private String temaNombre;
	private String fecha;
	private String orden;

	public CargaUnidadTema(){
		cursoCargaId = "";
		temaId		 = "";
		temaNombre   = "";
		fecha	     = "";
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

	public String getTemaId() {
		return temaId;
	}

	public void setTemaId(String temaId) {
		this.temaId = temaId;
	}

	public String getTemaNombre() {
		return temaNombre;
	}

	public void setTemaNombre(String temaNombre) {
		this.temaNombre = temaNombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}