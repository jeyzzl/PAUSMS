// Bean de Alum_Foto
package  aca.cultural.spring;

public class CompEvento{
	private String eventoId;
	private String eventoNombre;
	private String fecha;
	private String descripcion;
	private String capacidad;
	private String lugar;
	private String estado;
	private String nivel;
		
	public CompEvento(){
		eventoId		="";
		eventoNombre	="";
		fecha			="";
		descripcion		="";
		capacidad 		="";
		lugar			="";
		estado			="";
		nivel			="";	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}