package aca.alerta.spring;

public class AlertaDocAlum {
	private String periodoId;
	private String codigoPersonal;	
	private String documentoId;
	private byte[] archivo;	
	private String nombre;
	
	public AlertaDocAlum(){
		codigoPersonal	= "0";
		periodoId		= "0";
		documentoId		= "0";
		archivo			= null;			
		nombre			= "";				
	}

	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getDocumentoId() {
		return documentoId;
	}
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
