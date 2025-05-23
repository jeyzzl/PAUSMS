package aca.vista.spring;

public class Usuarios {
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String fNacimiento;
	private String genero;	
	private String usuario;
	private String clave;
	private String estado;	
	
	public Usuarios(){
		codigoPersonal	= "0";
		nombre			= "-";
		apellidoPaterno	= "-";
		apellidoMaterno	= "-";
		fNacimiento    	= "";
		genero	     	= "M";		
		usuario		    = "*";
		clave           = "*";
		estado          = "A";		
	}
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	public String getfNacimiento() {
		return fNacimiento;
	}


	public void setfNacimiento(String fNacimiento) {
		this.fNacimiento = fNacimiento;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

}