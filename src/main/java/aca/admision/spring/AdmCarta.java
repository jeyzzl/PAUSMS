package aca.admision.spring;

public class AdmCarta {
	
	private String folio;
	private String condicionId;
	private String condicionNombre;
	
	public AdmCarta() {
		folio 			= "0";
		condicionId		= "0";
		condicionNombre	= "-";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCondicionId() {
		return condicionId;
	}

	public void setCondicionId(String condicionId) {
		this.condicionId = condicionId;
	}

	public String getCondicionNombre() {
		return condicionNombre;
	}

	public void setCondicionNombre(String condicionNombre) {
		this.condicionNombre = condicionNombre;
	}
	
}
