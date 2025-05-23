package aca.conva.spring;

public class ConvPeriodo{
	
	private String periodoId;
	private String periodoNombre;
	private String fechaIni;
	private String fechaFin;
	private String carrera;
			
	public ConvPeriodo(){
		periodoId 		= "0";
		periodoNombre	= "-";
		fechaIni		= aca.util.Fecha.getHoy();
		fechaFin		= aca.util.Fecha.getHoy();
		carrera			= "0";
	}
		
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	
}