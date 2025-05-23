/**
 * 
 */
package aca.pg.archivos.spring;

public class PosArchivosProfesor {
	private String archivoId;
	private String folio;
	private String codigoPersonal;
	private String fecha;
	private String nombre;
	private String comentario;
	private String autorizacion;
	private String tamano;
	private long archivo;
	
	public PosArchivosProfesor(){
		archivoId		= "";
		folio			= "";
		codigoPersonal	= "";
		fecha			= "";
		nombre			= "";
		comentario		= "";
		autorizacion	= "";
		tamano			= "";
		archivo			= 0;
	}
	
	public String getArchivoId() {
		return archivoId;
	}

	public void setArchivoId(String archivoId) {
		this.archivoId = archivoId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public long getArchivo() {
		return archivo;
	}

	public void setArchivo(long archivo) {
		this.archivo = archivo;
	}
}