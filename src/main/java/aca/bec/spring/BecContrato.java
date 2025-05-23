package aca.bec.spring;


public class BecContrato {
	private String codigoPersonal;
	private String puestoId;
	private String fecha;
	private byte[] archivo;
	private String nombre;
	private String autorizado;
	
	public BecContrato(){
		codigoPersonal	= "0";
		puestoId		= "0";
		fecha			= "";
		archivo			= null;		
		nombre			= "-";
		autorizado 		= null;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(String puestoId) {
		this.puestoId = puestoId;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAutorizado() {
		return autorizado;
	}

	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}	
}