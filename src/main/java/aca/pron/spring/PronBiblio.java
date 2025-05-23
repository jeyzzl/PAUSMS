package aca.pron.spring;

public class PronBiblio {
	
	private String cursoCargaId;
	private String biblioId;
	private String biblioNombre;
	private String orden;
	
	public PronBiblio() {
		cursoCargaId 	= "-";
		biblioId 		= "0";
		biblioNombre 	= "-";
		orden 			= "0";
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getBiblioId() {
		return biblioId;
	}

	public void setBiblioId(String biblioId) {
		this.biblioId = biblioId;
	}

	public String getBiblioNombre() {
		return biblioNombre;
	}

	public void setBiblioNombre(String biblioNombre) {
		this.biblioNombre = biblioNombre;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
