package aca.carga.spring;

public class CargaGrupoProgramacion {

	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String orden;
	
	public CargaGrupoProgramacion(){
		cursoCargaId	= "";
		folio 			= "";
		fecha			= "";
		orden			= "";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
}
