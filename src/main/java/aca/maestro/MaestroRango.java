package aca.maestro;

public class MaestroRango {
	private String codigoPersonal;
	private String year;
	private String area;
	private String rangoAnterior;
	private String rangoRecomendado;
	private String respuesta;
	private String nombre;	
	
	public MaestroRango(){
		codigoPersonal		= "";
		year				= "0";
		area				= "-";
		nombre				= "-";		
		rangoAnterior		= "";
		rangoRecomendado    = "";
		respuesta     		= "";				
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRangoAnterior() {
		return rangoAnterior;
	}

	public void setRangoAnterior(String rangoAnterior) {
		this.rangoAnterior = rangoAnterior;
	}

	public String getRangoRecomendado() {
		return rangoRecomendado;
	}

	public void setRangoRecomendado(String rangoRecomendado) {
		this.rangoRecomendado = rangoRecomendado;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}