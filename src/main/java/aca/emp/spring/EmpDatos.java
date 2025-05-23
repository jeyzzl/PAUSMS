// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpDatos{
	private String idEmpleado;
	private String puesto;	
	private String departamento;
	private String status;
	private String cotejado;
	private String impreso;
	private String id;
	private String nombre;
	private String apellidos;
	private String tipocred;
	
	public EmpDatos(){
		idEmpleado 		= "0";
		puesto			= "0";
		departamento 	= "0";
		status 			= "";
		cotejado		= "N";
		impreso			= "";
		id 				= "0";
		nombre			= "";
		apellidos 		= "";
		tipocred 		= "";
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCotejado() {
		return cotejado;
	}

	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipocred() {
		return tipocred;
	}

	public void setTipocred(String tipocred) {
		this.tipocred = tipocred;
	}
	
}