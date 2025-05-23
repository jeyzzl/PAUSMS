package aca.cred.spring;

public class CredDependiente {
	
	private String idDependiente;	
	private String idEmpleado;
	private String depNombre;
	private String depApellidos;
	private String empNombre;
	private String empApellidos;	
	private String relacion;
	private String puesto;
	private String departamento;
	private String cotejado;
	private String fecha;
	private String folio;
	private String fActualiza;
	
	public CredDependiente() {
		idDependiente		= "0";	
		idEmpleado			= "0";
		depNombre 			= "-";
		depApellidos 		= "-";
		empNombre 			= "-";
		empApellidos		= "-";;	
		relacion			= "-";;
		puesto				= "-";
		departamento		= "-";
		cotejado			= "-";
		fecha				= "-";
		folio				= "-";
		fActualiza			= "-";
	}

	public String getIdDependiente() {
		return idDependiente;
	}

	public void setIdDependiente(String idDependiente) {
		this.idDependiente = idDependiente;
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getDepNombre() {
		return depNombre;
	}

	public void setDepNombre(String depNombre) {
		this.depNombre = depNombre;
	}

	public String getDepApellidos() {
		return depApellidos;
	}

	public void setDepApellidos(String depApellidos) {
		this.depApellidos = depApellidos;
	}

	public String getEmpNombre() {
		return empNombre;
	}

	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
	}

	public String getEmpApellidos() {
		return empApellidos;
	}

	public void setEmpApellidos(String empApellidos) {
		this.empApellidos = empApellidos;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCotejado() {
		return cotejado;
	}

	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getfActualiza() {
		return fActualiza;
	}

	public void setfActualiza(String fActualiza) {
		this.fActualiza = fActualiza;
	}	
}
