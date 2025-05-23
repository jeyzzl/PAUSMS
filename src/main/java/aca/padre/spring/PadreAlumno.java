 // Bean de datos personales del padre 
 package  aca.padre.spring;
 
 public class PadreAlumno{
 	private String padreId;
 	private String codigoPersonal;
 	private String fechaAlta;
 	private String fechaAutoriza;
 	private String estado;
 	
 	public PadreAlumno(){
 		padreId				= "";
 		codigoPersonal		= "";
 		fechaAlta			= "";
 		fechaAutoriza		= "";
 		estado				= "";
 	}

 	public String getPadreId() {
		return padreId;
	}

	public void setPadreId(String padreId) {
		this.padreId = padreId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaAutoriza() {
		return fechaAutoriza;
	}

	public void setFechaAutoriza(String fechaAutoriza) {
		this.fechaAutoriza = fechaAutoriza;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

 }