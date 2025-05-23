package aca.vigilancia.spring;

public class VigTipoMulta {
	
	private String tipoId;
	private String tipoNombre;
	private String costo;
	
	
	public VigTipoMulta(){
		tipoId				= "";
		tipoNombre			= "";
		costo				= "";	
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public String getCosto() {
		return costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}
}