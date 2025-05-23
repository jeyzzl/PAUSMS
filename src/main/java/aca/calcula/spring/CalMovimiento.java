package  aca.calcula.spring;

public class CalMovimiento{
	
	private String movimientoId;
	private String codigoPersonal;	
	private String conceptoId;
	private String tipo;
	private String importe;
	private String cargaId;
	private String bloqueId;
	private String fecha;
		
	public CalMovimiento(){
		movimientoId		= "0";
		codigoPersonal		= "0";
		conceptoId			= "0";
		tipo				= "";
		importe				= "0";
		cargaId				= "0";
		bloqueId			= "1";
		fecha				= null;	
	}

	public String getMovimientoId() {
		return movimientoId;
	}

	public void setMovimientoId(String movimientoId) {
		this.movimientoId = movimientoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(String conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}