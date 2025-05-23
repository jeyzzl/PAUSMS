package aca.podium.spring;

public class Aspirante {
	
	private int id;
	private int folio;
	private String grado;
	
	public Aspirante() {
		folio	= 0;
		grado	= "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}
}
