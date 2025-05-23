package aca.internado.spring;

public class IntAlumReporte {
	
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String reporteId;
	private String comentario;
	private String usuario;
	private String cantidad;
	private String dormitorio;
	
	public IntAlumReporte(){		
		codigoPersonal		= "0";
		folio				= "0";
		fecha				= "";
		reporteId			= "0";
		cantidad			= "0";
		comentario			= "-";
		usuario				= "";
		cantidad			= "";
		dormitorio			= "0";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getReporteId() {
		return reporteId;
	}

	public void setReporteId(String reporteId) {
		this.reporteId = reporteId;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
}