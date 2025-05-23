package aca.alumno.spring;

public class AlumPatrocinador {
	private String codigoPersonal;
	private String periodoId;
	private String patrocinadorId;
	private String porcentaje;
	private String cantidad;
	
	public AlumPatrocinador() {
		codigoPersonal 	= "0";
		periodoId		= "0";
		patrocinadorId 	= "0";
		porcentaje	   	= "0";
		cantidad		= "0";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	public String getPatrocinadorId() {
		return patrocinadorId;
	}
	public void setPatrocinadorId(String patrocinadorId) {
		this.patrocinadorId = patrocinadorId;
	}
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
