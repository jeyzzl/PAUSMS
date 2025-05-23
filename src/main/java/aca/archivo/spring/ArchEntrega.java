//Beans para la tabla ARCH_DOCALUM
package aca.archivo.spring;

public class ArchEntrega {
	private String codigoPersonal;
	private String folio;
	private String documentos;
	private byte[] identificacion;
	private byte[] poder;
	private byte[] envio;
	private byte[] firma;
	private byte[] extra;
	private String fecha;
	
	public ArchEntrega(){
		codigoPersonal 	= "";
		folio 			= "";
		documentos		= "";
		identificacion	= null;
		poder			= null;
		envio			= null;
		firma			= null;
		extra			= null;
		fecha			= "";
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

	public String getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public byte[] getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(byte[] identificacion) {
		this.identificacion = identificacion;
	}

	public byte[] getPoder() {
		return poder;
	}

	public void setPoder(byte[] poder) {
		this.poder = poder;
	}

	public byte[] getEnvio() {
		return envio;
	}

	public void setEnvio(byte[] envio) {
		this.envio = envio;
	}

	public byte[] getFirma() {
		return firma;
	}

	public void setFirma(byte[] firma) {
		this.firma = firma;
	}

	public byte[] getExtra() {
		return extra;
	}

	public void setExtra(byte[] extra) {
		this.extra = extra;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
}		