package  aca.calcula.spring;

public class CalPagare{
	
	private String pagareId;
	private String pagareNombre;
	private String fecha;
	private String cargaId;
	private String bloqueId;
	
		
	public CalPagare(){
		pagareId			= "";
		pagareNombre		= "-";
		fecha				= null;
		cargaId				= "0";
		bloqueId			= "0";
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
	
	
}