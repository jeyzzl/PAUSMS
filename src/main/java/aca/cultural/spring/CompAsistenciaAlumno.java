package  aca.cultural.spring;

public class CompAsistenciaAlumno{
	private String eventoId;
	private String codigoPersonal;
	private String planId;
	private String entrada;
	private String salida;
	private String entradaHora;
	private String salidaHora;
		
	public CompAsistenciaAlumno(){
		eventoId		= "";
		codigoPersonal	= "";
		planId			= "0";
		entrada			= "N";
		salida			= "N";
		entradaHora		= "-";
		salidaHora 		= "-";
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getEntradaHora() {
		return entradaHora;
	}

	public void setEntradaHora(String entradaHora) {
		this.entradaHora = entradaHora;
	}

	public String getSalidaHora() {
		return salidaHora;
	}

	public void setSalidaHora(String salidaHora) {
		this.salidaHora = salidaHora;
	}

}