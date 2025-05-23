package aca.tit.spring;

public class TitCadena {

	String folio;
    String codigoPersonal;
    String cadena;
    String sello;
    
    public TitCadena() {
    	folio			= "";
        codigoPersonal	= "";
        cadena			= "";
        sello			= "";
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

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}
    
}
