package aca.vigilancia.spring;

public class VigInfraccion {
	private String folio;
	private String fecha;
	private String autoId;
	private String tipoId;
	private String descripcion;
	private String multa;
		
	public VigInfraccion(){
		folio				= "";
		fecha				= "";
		autoId				= "";
		tipoId				= ""; 		
		descripcion			= "";
		multa				= "";	
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

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}
}