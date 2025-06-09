package aca.admision.spring;

public class AdmUsuario{
	
	private String usuarioId;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String fechaNac;
	private String email;
	private String cuenta;
	private String clave;
	private String matricula;
	private String telefono;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String genero;
	private String estadoCivil;
	private String religionId;
	private String fecha;
	private String estado;
	private String codigo;
	private String nacionalidad;
	private String empleado;
	private String institucionId;

	public AdmUsuario() {
		usuarioId 		= "0";
		nombre 			= "";
		apellidoPaterno = "";
		apellidoMaterno = "";
		fechaNac 		= "";
		email 			= "";
		cuenta 			= "";
		clave 			= "";
		matricula 		= "-";
		telefono 		= "";
		paisId 			= "0";
		estadoId 		= "0";
		ciudadId 		= "0";
		genero 			= "";
		estadoCivil 	= "S";
		religionId 		= "0";
		estado			= "0";
		codigo			= "0";
		nacionalidad 	= "0";
		empleado		= "N";
		institucionId 	= "0";
	}
	
	public String getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
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
	public void setApellidoMaterno(String apelidoMaterno) {
		this.apellidoMaterno = apelidoMaterno;
	}
	
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getPaisId() {
		return paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}
	
	public String getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}
	
	public String getCiudadId() {
		return ciudadId;
	}
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public String getReligionId() {
		return religionId;
	}
	public void setReligionId(String religionId) {
		this.religionId = religionId;
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getInstitucionId() {
		return institucionId;
	}
	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}

	
}