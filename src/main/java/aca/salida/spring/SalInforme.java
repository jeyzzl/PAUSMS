package aca.salida.spring;

public class SalInforme {
	
	private String salidaId;
	private String incidentes;
	private String observaciones;
	private String fecha;
	private String usuario;
	
	public SalInforme(){
		salidaId  		= "0";
		incidentes 		= "-";
		observaciones  	= "-";
		fecha  			= aca.util.Fecha.getHoy();
		usuario  		= "-";
	}

	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getIncidentes() {
		return incidentes;
	}

	public void setIncidentes(String incidentes) {
		this.incidentes = incidentes;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
