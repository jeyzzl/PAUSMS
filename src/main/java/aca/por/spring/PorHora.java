// Bean del Catalogo Curso
package  aca.por.spring;

public class PorHora{
	
	private String folio;
	private String dia;
	private String salonId;
	private String equipoId;
	private String hora;
	private String codigoPersonal;	
	
	public PorHora(){
		folio			= "";
		dia				= "";
		salonId			= "";
		equipoId		= "";
		hora			= "";
		codigoPersonal	= "";		
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
	public String getSalonId() {
		return salonId;
	}

	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	public String getEquipoId() {
		return equipoId;
	}

	public void setEquipoId(String equipoId) {
		this.equipoId = equipoId;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
}