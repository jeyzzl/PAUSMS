package aca.trabajo.spring;

public class TrabDepartamento {
	private String deptId;
	private String nombre;
	private String detalles;
	private String estado;
	private String codigo_personal;
	
	public TrabDepartamento() {
		deptId 			= "0";
		nombre 			= "-";
		detalles 		= "-";
		estado 			= "A";
		codigo_personal = "-";
	}

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigo_personal() {
		return codigo_personal;
	}
	public void setCodigo_personal(String codigo_personal) {
		this.codigo_personal = codigo_personal;
	}
	
	

}
