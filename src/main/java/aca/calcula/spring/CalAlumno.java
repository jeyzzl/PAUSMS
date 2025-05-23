package  aca.calcula.spring;

public class CalAlumno{
	
	private String codigoPersonal;	
	private String cargaId;
	private String bloqueId;
	private String numPagare;
	private String confirmar;
	private String fecha;
	private String cobroMatricula;
	private String saldo;
	private String porcentaje;
		
	public CalAlumno(){
		codigoPersonal		= "0";
		cargaId				= "0";
		bloqueId			= "1";
		numPagare			= "1";
		confirmar 			= "N";
		fecha				= null;	
		cobroMatricula		= "S";
		saldo				= "0";
		porcentaje			= "35";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	
	public String getNumPagare() {
		return numPagare;
	}
	public void setNumPagare(String numPagare) {
		this.numPagare = numPagare;
	}
	
	public String getConfirmar() {
		return confirmar;
	}
	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCobroMatricula() {
		return cobroMatricula;
	}
	public void setCobroMatricula(String cobroMatricula) {
		this.cobroMatricula = cobroMatricula;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
}