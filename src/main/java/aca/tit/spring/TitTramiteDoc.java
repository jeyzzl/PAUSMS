package aca.tit.spring;

public class TitTramiteDoc {

	String tramiteId;
    String folio;
    String fecha;    
    
    public TitTramiteDoc() {
    	tramiteId 	= "";
    	folio 		= "";
    	fecha		= "";    	
    }

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
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
    
}
