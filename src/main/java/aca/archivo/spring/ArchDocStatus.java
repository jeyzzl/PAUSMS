//Beans de la tabla ARCH_DOCSTATUS
package aca.archivo.spring;

public class ArchDocStatus {
    private String idDocumento;
	private String idStatus;
	private String estado;
	private String valido;
	
	public ArchDocStatus(){
		idDocumento 	= "0";
		idStatus		= "0";
		estado			= "A";
		valido			= "N";
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getValido() {
		return valido;
	}

	public void setValido(String valido) {
		this.valido = valido;
	}
	
}