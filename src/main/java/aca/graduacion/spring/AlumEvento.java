// Clase para la tabla de Modulo
package aca.graduacion.spring;

public class AlumEvento{	
	private String eventoId;
	private String eventoNombre;
	private String fecha;
	private String estado;
	private String archivo;
	
	public AlumEvento(){
		eventoId		= "0";
		eventoNombre	= "-";
		fecha			= "";
		estado			= "A";
		archivo			= "N";
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

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
}