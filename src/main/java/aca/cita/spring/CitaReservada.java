package aca.cita.spring;

public class CitaReservada {
    
	private String codigoPersonal;
	private String eventoId;
	private String dia;
	private String periodoId;
	private String fecha;
	private String estado;
	
	public CitaReservada() {    	
		codigoPersonal	 	= "";
		eventoId			= "";
		dia					= "-";
		periodoId			= "";
		fecha 				= "-";
		estado 				= "-";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getEventoId() {
		return eventoId;
	}
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}