package aca.portafolio.spring;

public class PorEstudio {
	
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String nivelId;
	private String titulo;
	private String hojas;
	
	public PorEstudio(){		
		codigoPersonal 	= "";
		folio			= "";
		fecha 			= "";
		nivelId			= "";
		titulo			= "";
		hojas 			= ""; 
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getHojas() {
		return hojas;
	}

	public void setHojas(String hojas) {
		this.hojas = hojas;
	}
	
}