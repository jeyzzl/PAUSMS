package aca.nse.spring;

public class NseEncuesta{
	private String encuestaId;
	private String encuestaNombre;
	private String fechaIni;	
	private String fechaFin;	
	
	public NseEncuesta(){		
		encuestaId 		= "0";
		encuestaNombre 	= "";
		fechaIni 		= null;		
		fechaFin 		= null;		
	}

	public String getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(String encuestaId) {
		this.encuestaId = encuestaId;
	}

	public String getEncuestaNombre() {
		return encuestaNombre;
	}

	public void setEncuestaNombre(String encuestaNombre) {
		this.encuestaNombre = encuestaNombre;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
}