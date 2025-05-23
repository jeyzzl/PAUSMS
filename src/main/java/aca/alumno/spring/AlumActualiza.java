package aca.alumno.spring;


public class AlumActualiza {
	private String codigoPersonal;
	private String codigoEmpleado;
	private String fecha;
	private String estado;	
	
	public AlumActualiza(){
		codigoPersonal		= "";
		codigoEmpleado		= "";
		fecha				= "";
		estado				= "";		
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
