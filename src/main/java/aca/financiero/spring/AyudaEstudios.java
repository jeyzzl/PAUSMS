package aca.financiero.spring;

public class AyudaEstudios {
	private String id;
	private String alumno;
	private String matricula;
	private String ensenanza;
	private String internado;	
	private String status;
	private String empleadoId;
	private String dependienteId;
	private String dateCaptura;
	
	public AyudaEstudios(){
		id				= "0";
		alumno			= "0";
		matricula		= "0";
		ensenanza		= "0";		
		internado		= "0";
		status			= "A";
		empleadoId		= "0";
		dependienteId	= "0";
		dateCaptura		= "01/01/2000";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEnsenanza() {
		return ensenanza;
	}

	public void setEnsenanza(String ensenanza) {
		this.ensenanza = ensenanza;
	}

	public String getInternado() {
		return internado;
	}

	public void setInternado(String internado) {
		this.internado = internado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}

	public String getDependienteId() {
		return dependienteId;
	}

	public void setDependienteId(String dependienteId) {
		this.dependienteId = dependienteId;
	}

	public String getDateCaptura() {
		return dateCaptura;
	}

	public void setDateCaptura(String dateCaptura) {
		this.dateCaptura = dateCaptura;
	}
	
}