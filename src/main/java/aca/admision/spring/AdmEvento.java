package aca.admision.spring;

public class AdmEvento {
    private String eventoId;
    private String eventoNombre;
    private String estado;
    private String lugar;
    private String orden;
    private String fecha;
    
    public AdmEvento() {    	
    	eventoId	 	= "0";
    	eventoNombre	= "-";
    	estado			= "A";
    	lugar			= "-";
    	orden 			= "";
    	fecha 			= "";
    } 
    
	public String getEventoId() {
		return eventoId;
	}
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	public String getEventoNombre() {
		return eventoNombre;
	}
	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}    
}