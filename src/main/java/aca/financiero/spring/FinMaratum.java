package aca.financiero.spring;

public class FinMaratum {
	private String id;
	private String codigoPersonal;
	private String planId;
	private String fecha;
	private byte[] archivo;
	private String nombre;
	private String usuario;
	private String estado;	
	
	public FinMaratum(){
		id 				= "0";
		codigoPersonal	= "0000000";
		planId			= "00000000";
		fecha			= "01/01/2000";
		archivo			= null;		
		nombre 			= "-";
		usuario			= "0000000";
		estado			= "S";		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
		
}