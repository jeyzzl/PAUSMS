package aca.admision.spring;

public class AdmPruebaAlumno {
	private String folio;
	private String pruebaId;
	private String usuario;	
	private String fecha;
		
	public AdmPruebaAlumno(){
		folio 			= "0";
		pruebaId 		= "0";
		usuario 		= "0";		
		fecha 			= null;
	}
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPruebaId() {
		return pruebaId;
	}
	public void setPruebaId(String pruebaId) {
		this.pruebaId = pruebaId;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
}