package  aca.cultural.spring;

public class CompAsistencia{
	private String eventoId;
	private String nombre;
	private String estado;
	private String fecha;
	private String hora;
		
	public CompAsistencia(){
		eventoId	= "";
		nombre		= "";
		estado		= "";
		fecha		= aca.util.Fecha.getHoyReversa();
		hora 		= "";
	}

	public String getEventoId() {
		return eventoId;
	}
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
}