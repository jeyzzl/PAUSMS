package  aca.calcula.spring;

public class CalPagareAlumno{
	
	private String codigoPersonal;
	private String pagareId;
	private String pagareNombre;
	private String fecha;
	private String cargaId;
	private String bloqueId;
	private String importe;
	
	public CalPagareAlumno(){
		codigoPersonal		= "0";
		pagareId			= "";
		pagareNombre		= "-";
		fecha				= null;
		cargaId				= "0";
		bloqueId			= "0";
		importe				= "0";
	}

	public String getPagareId() {
		return pagareId;
	}
	public void setPagareId(String pagareId) {
		this.pagareId = pagareId;
	}

	public String getPagareNombre() {
		return pagareNombre;
	}
	public void setPagareNombre(String pagareNombre) {
		this.pagareNombre = pagareNombre;
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

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}	
}