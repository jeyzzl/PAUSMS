package aca.cita.spring;

public class CitaEvento {
    private String eventoId;
    private String eventoNombre;
    private String estado;
    
    public CitaEvento() {    	
    	eventoId	 	= "0";
    	eventoNombre	= "-";
    	estado			= "A";    	
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
    
}