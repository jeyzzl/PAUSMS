//Beans para la tabla ARCH_DOCALUM
package aca.archivo.spring;

public class ArchDocAlum {
	private String matricula;
	private String idDocumento;
	private String idStatus;
	private String fecha;
	private String usuario;
	private String cantidad;
	private String ubicacion;
	private String incorrecto;	
	
	public ArchDocAlum(){
		matricula 	= "";
		idDocumento = "";
		idStatus	= "";
		fecha		= "";
		usuario		= "";
		cantidad	= "";
		ubicacion	= "";
		incorrecto	= "N";	 
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getIncorrecto() {
		return incorrecto;
	}

	public void setIncorrecto(String incorrecto) {
		this.incorrecto = incorrecto;
	}	
}		