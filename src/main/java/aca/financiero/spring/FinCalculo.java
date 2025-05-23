// Bean Documento de Calculco de Cobro
package  aca.financiero.spring;

public class FinCalculo{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private byte[] archivo;
	private String nombre;
	private String fecha;
	
	public FinCalculo(){
		codigoPersonal 	= "0";
		cargaId			= "0";
		bloqueId 		= "0";
		archivo			= null;
		nombre			= "";
		fecha			= "-";
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

	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}