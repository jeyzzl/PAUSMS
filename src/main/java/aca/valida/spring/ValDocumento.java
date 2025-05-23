package aca.valida.spring;

public class ValDocumento {
	private String clave;
	private String tipo;	
	private String folio;
	private String codigoPersonal;	
	
	public ValDocumento(){
		clave 			= "0";
		tipo			= "0";
		folio			= "0";
		codigoPersonal	= "0";					
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
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

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	
}