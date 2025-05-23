package aca.log.spring;

public class LogAlumno {
	private String id;
	private String tabla;
	private String operacion;
	private String ip;
	private String fecha;
	private String usuario;
	private String datos;
	
	public LogAlumno() {
		id 				= "0";
		tabla			= "X";
		operacion		= "-";
		ip				= "0";
		fecha 			= "";
		usuario			= "0";
		datos 			= "-";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}	
}