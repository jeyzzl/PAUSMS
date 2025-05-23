package aca.vigilancia.spring;

public class VigIngreso {
	private String folio;
	private String fecha;
	private String codigoId;
	private String resId;
	private String dormi;
	private String tipo;
		
	public VigIngreso(){
		folio				= "";
		fecha				= "";
		codigoId			= "";
		resId				= ""; 		
		dormi				= ""; 		
		tipo				= "";
	} 

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getCodigoId() {
		return codigoId;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getDormi() {
		return dormi;
	}

	public void setDormi(String dormi) {
		this.dormi = dormi;
	}
	
}