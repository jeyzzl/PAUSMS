package aca.acceso.spring;

public class AccesoValida {

	private String documentoId;
	private String codigoPersonal;
	private String fecha;
	private String llave;
	private String codigo;
	private String tipo;
	
	public AccesoValida() {
		documentoId		= "0";
		codigoPersonal	= "0";
		fecha			= "";
		llave			= "X";		
		codigo			= "";
		tipo			= "0";
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
