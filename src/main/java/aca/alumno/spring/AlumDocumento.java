package aca.alumno.spring;


public class AlumDocumento {
	private String codigoPersonal;
	private String folio;
	private String planId;
	private byte[] archivo;
	private String fecha;
	private String fechaCrea;
	private String estado;	
	private String nombre;	
	private String tipo;	
	
	public AlumDocumento(){
		codigoPersonal	= "";
		folio			= "";
		planId			= "";
		archivo			= null;
		fecha			= "";
		fechaCrea		= "";
		estado			= "";		
		nombre			= "";		
		tipo			= "";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaCrea() {
		return fechaCrea;
	}

	public void setFechaCrea(String fechaCrea) {
		this.fechaCrea = fechaCrea;
	}
	
}
