//Beans de la tabla ARCH_STATUS
package aca.archivo.spring;

public class ArchStatus {
    private String IdStatus;
	private String Descripcion;
	
	public ArchStatus(){
		IdStatus 	= "0";
		Descripcion = "-";
	}
	
	public String getIdStatus() {
		return IdStatus;
	}

	public void setIdStatus(String idStatus) {
		IdStatus = idStatus;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
}