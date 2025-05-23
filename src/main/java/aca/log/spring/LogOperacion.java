package aca.log.spring;

public class LogOperacion {
	
	public String tabla;
	public String operacion;
	public String ip;
	public String fecha;
	public String usuario;
	public String datos;
	
	public LogOperacion() {
		tabla		= "";
		operacion	= "";
		ip			= "";
		fecha		= "";
		usuario		= "";
		datos		= "";
	}

	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}
	
}
