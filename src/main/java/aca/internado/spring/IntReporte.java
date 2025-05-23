package aca.internado.spring;

public class IntReporte {
	
	private String reporteId;
	private String reporteNombre;
	
	public IntReporte() {
		reporteId		= "";
		reporteNombre	= "";
	}

	public String getReporteId() {
		return reporteId;
	}

	public void setReporteId(String reporteId) {
		this.reporteId = reporteId;
	}

	public String getReporteNombre() {
		return reporteNombre;
	}

	public void setReporteNombre(String reporteNombre) {
		this.reporteNombre = reporteNombre;
	}
}