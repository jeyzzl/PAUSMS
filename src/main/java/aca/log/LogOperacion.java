/*
 * Created on 28-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogOperacion {
	private String tabla,operacion,ip,usuario,datos,fecha;	
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		datos			= rs.getString("DATOS");
		ip				= rs.getString("IP");
		operacion		= rs.getString("OPERACION");
		tabla			= rs.getString("TABLA");
		usuario			= rs.getString("USUARIO");
		fecha			= rs.getString("FECHA");
	}
	
	
	
}