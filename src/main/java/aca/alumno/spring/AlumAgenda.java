package aca.alumno.spring;

public class AlumAgenda {
	private String codigoPersonal;	
	private String entregado;
	private String fecha;
	private String cargaId;
	private String bloqueId;
	
	public AlumAgenda(){
		codigoPersonal	= "0";		
		entregado		= "0";
		fecha			= aca.util.Fecha.getHoy();
		cargaId			= "0";
		bloqueId		= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getEntregado() {
		return entregado;
	}
	public void setEntregado(String entregado) {
		this.entregado = entregado;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloqueId() {
		return bloqueId;
	}
	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}	
}