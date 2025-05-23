package aca.well;

public class WellPermiso {

	private String periodo;
	private String codigoEmpleado;
	private String fecha;
	private String ip;
	
	public WellPermiso(){
		periodo			= "";
		codigoEmpleado	= "";
		fecha			= "";
		ip				= "";
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
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
	
}
