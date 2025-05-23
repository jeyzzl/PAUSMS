package aca.emp.spring;

public class EmpleadoDependientes {
	
	private String empleadoId;
	private String id;	
	private String nombre;	
	private String bday;
	private String relacionId;
	private String matricula;	
	
	public EmpleadoDependientes(){
		empleadoId 		= "";
		id           	= "";		
		nombre      	= "";		
		bday			= "";		
		relacionId      = "0";
		matricula   	 = "";
	}

	public String getEmpleadoId() {
		return empleadoId;
	}
	
	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getBday() {
		return bday;
	}
	
	public void setBday(String bday) {
		this.bday = bday;
	}
	
	public String getRelacionId() {
		return relacionId;
	}
	
	public void setRelacionId(String relacionId) {
		this.relacionId = relacionId;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}