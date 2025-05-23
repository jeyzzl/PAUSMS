// Bean de documentos de admision del alumno
package  aca.admision.spring;

public class AdmPrueba{
	private String pruebaId;
	private String nombre;	
	private String descripcion;
	private String accion;
	


	public AdmPrueba(){
		pruebaId 			= "";
		nombre 				= "";
		descripcion			= "";
		accion				= "";
		
	}



	public String getPruebaId() {
		return pruebaId;
	}



	public void setPruebaId(String pruebaId) {
		this.pruebaId = pruebaId;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getAccion() {
		return accion;
	}



	public void setAccion(String accion) {
		this.accion = accion;
	}


	
}