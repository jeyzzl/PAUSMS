// Bean del Catalogo de Grupos
package  aca.carga.spring;

public class CargaGrupoHora{
	private String folio;
	private String cursoCargaId;
	private String salonId;
	private String dia;
	private String horarioId;
	private String periodo;
	private String bloqueId;
	
	public CargaGrupoHora(){
		folio 			= "0";
		cursoCargaId	= "0";
		salonId			= "0";
		dia				= "0";
		horarioId		= "0";
		periodo			= "0";
		bloqueId		= "0";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getSalonId() {
		return salonId;
	}

	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}