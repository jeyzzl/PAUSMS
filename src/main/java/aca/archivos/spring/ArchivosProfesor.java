package aca.archivos.spring;

public class ArchivosProfesor {
	private String archivoId;
	private int folio;
	private String codigoPersonal;
	private String fecha;
	private String nombre;
	private String comentario;
	private String autorizacion;
	private long tamano;
	private long oid;
	private byte[] archivoNuevo;
	
	public String getArchivoId() {
		return archivoId;
	}
	public void setArchivoId(String archivoId) {
		this.archivoId = archivoId;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
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
	public long getTamano() {
		return tamano;
	}
	public void setTamano(long tamano) {
		this.tamano = tamano;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public byte[] getArchivoNuevo() {
		return archivoNuevo;
	}
	public void setArchivoNuevo(byte[] archivoNuevo) {
		this.archivoNuevo = archivoNuevo;
	}
}