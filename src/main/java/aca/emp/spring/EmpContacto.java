package aca.emp.spring;

public class EmpContacto {	
	private String empleadoId;
	private String correo;
	private String telefono;
	
	public EmpContacto(){		
		empleadoId		= "0";
		correo			= "email";
		telefono		= "phone";
	}

	public String getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}