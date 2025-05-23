// Bean del Catalogo de Horarios
package  aca.catalogo.spring;

public class CatHorario{
	private String horarioId;
	private String facultadId;
	private String descripcion;
	private String estado;
	private String acceso;
		
	public CatHorario(){
		horarioId		= "0";
		facultadId		= "0";
		descripcion		= "-";
		estado			= "A";
		acceso			= "";
	}
	
	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}
	
}