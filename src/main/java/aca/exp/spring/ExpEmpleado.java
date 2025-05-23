//Beans para la tabla ARCH_DOCALUM
package aca.exp.spring;

public class ExpEmpleado {
	private String codigoPersonal;
	private String documentoId;	
	private String hoja;
	private String fecha;
	private String usuario;
	private byte[] archivo;
	private String nombre;
		
	
	public ExpEmpleado(){
		codigoPersonal 	= "0";
		documentoId 	= "0";
		hoja			= "0";
		fecha			= null;
		usuario			= "0";
		archivo			= null;	
		nombre 			= "-";
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

	public String getHoja() {
		return hoja;
	}
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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