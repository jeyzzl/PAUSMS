package aca.archivos.spring;

public class ArchivosAlumno {
	private String archivoId;
	private String folio;
	private String codigoPersonal;
	private String fecha;
	private String nombre;
	private String comentario;
	private String tamano;
	private String status;
	private int archivo;
	private byte[] archivoNuevo;
	private String actividadId;
	private String evaluacionId;
	
	public ArchivosAlumno(){
		archivoId		= "";
		folio			= "0";
		codigoPersonal	= "0";
		fecha			= aca.util.Fecha.getHoy();
		nombre			= "";
		comentario		= "";
		tamano			= "";
		status			= "";
		archivo			= 0;
		archivoNuevo 	= null;
		actividadId		= "0";
		evaluacionId	= "0";
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

	public String getTamano() {
		return tamano;
	}
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getArchivo() {
		return archivo;
	}
	public void setArchivo(int archivo) {
		this.archivo = archivo;
	}

	public byte[] getArchivoNuevo() {
		return archivoNuevo;
	}
	public void setArchivoNuevo(byte[] archivoNuevo) {
		this.archivoNuevo = archivoNuevo;
	}

	public String getActividadId() {
		return actividadId;
	}
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}
	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}	
}