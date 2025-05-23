package aca.carga.spring;

public class CargaGrupoBiblio {
	private String cursoCargaId;
	private String biblioId;
	private String bibliografia;
	private String orden;
	
	public CargaGrupoBiblio(){
		cursoCargaId    ="";
		biblioId		="";
		bibliografia    ="";
		orden           ="";		
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

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}