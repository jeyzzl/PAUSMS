//Bean del Catalogo Cargas
package  aca.carga.spring;

public class CargaArchivo{
	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String nombre;
	private String evaluacionId;
	private String actividadId;
	private String usuarioOrigen;
	private String usuarioDestino;
	private String comentario;
	private String ruta;
	private String estado;
	
	public CargaArchivo(){
		cursoCargaId	= "";
		folio			= "";
		fecha			= "";
		nombre			= "";
		evaluacionId	= "0";
		actividadId		= "0";
		usuarioOrigen	= "";
		usuarioDestino	= "";
		comentario		= "";
		ruta			= "";
		estado			= "";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getActividadId() {
		return actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
