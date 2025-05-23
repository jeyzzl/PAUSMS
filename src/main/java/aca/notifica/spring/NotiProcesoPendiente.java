package aca.notifica.spring;

public class NotiProcesoPendiente {
	private String id;
	private String codigoPersonal;
	/**
	 * Solo debe haber un tipo de mensaje por persona y este debe ser actualizado si llega una segunda notificación del mismo tipo
	 */
	private String tipo;
	private String fechaInicio;
	private String datos;
	
	public NotiProcesoPendiente(){
		id				= "0";
		codigoPersonal	= "0";
		tipo			= "0";
		fechaInicio		= aca.util.Fecha.getHoy();		
		datos			= "";	
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the datos
	 */
	public String getDatos() {
		return datos;
	}

	/**
	 * @param datos the datos to set
	 */
	public void setDatos(String datos) {
		this.datos = datos;
	}
	
}