package aca.musica.spring;

public class MusiHorarioAlumno {
	
	private String codigoPersonal;
 	private String folio;
 	private String cursoCargaId;
 	
 	public MusiHorarioAlumno(){
 		codigoPersonal	= "";
 		folio			= "";
 		cursoCargaId			= "";
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

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}
 	
}
