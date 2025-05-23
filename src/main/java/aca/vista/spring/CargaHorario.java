// Clase para la vista CARGA_ACADEMICA
package  aca.vista.spring;

public class CargaHorario{
	
	private String cursoCargaId;
	private String salonId;
	private String dia;
	private String horaInicio;
	private String minutoInicio;
	private String horaFinal;
	private String minutoFinal;
	private String horarioId;
	private String periodo;
	private String bloqueId;
	private String folio;
	
	public CargaHorario(){
		cursoCargaId	= "";
		salonId			= "";
		dia				= "";
		horaInicio		= "";
		minutoInicio	= "";
		horaFinal		= "";
		minutoFinal		= "";
		horarioId		= "";
		periodo			= "";
		bloqueId		= "";
		folio			= "0";
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

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(String minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getMinutoFinal() {
		return minutoFinal;
	}

	public void setMinutoFinal(String minutoFinal) {
		this.minutoFinal = minutoFinal;
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