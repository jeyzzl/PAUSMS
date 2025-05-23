// Bean de Alum_Foto
package  aca.cultural.spring;

public class CompEventoImagen{
	private String eventoId;
	private String imagenId;
	private String descripcion;
	private byte[] imagen;
		
	public CompEventoImagen(){
		eventoId				= "";
		imagenId				= "";
		descripcion				= "";
		imagen 					= null;
	}
	
	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getImagenId() {
		return imagenId;
	}

	public void setImagenId(String imagenId) {
		this.imagenId = imagenId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
}