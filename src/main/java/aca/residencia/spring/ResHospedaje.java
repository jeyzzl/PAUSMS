package aca.residencia.spring;

public class ResHospedaje {
	
	private String id;
	private String nombre;
	private String apellidos;
	private String nomina;
	private String direccion;
	private String telefono;
	private String cupoHombres;
	private String cupoMujeres;
	private String cuartos;
	private String estado;
	
	public ResHospedaje() {
		id 			= "";
		nombre 		= "";
		apellidos 	= "";
		nomina 		= "";
		direccion 	= "";
		telefono 	= "";
		cupoHombres = "";
		cupoMujeres = "";
		cuartos 	= "";
		estado		= "";
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

	public String getNomina() {
		return nomina;
	}

	public void setNomina(String nomina) {
		this.nomina = nomina;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCupoHombres() {
		return cupoHombres;
	}

	public void setCupoHombres(String cupoHombres) {
		this.cupoHombres = cupoHombres;
	}

	public String getCupoMujeres() {
		return cupoMujeres;
	}

	public void setCupoMujeres(String cupoMujeres) {
		this.cupoMujeres = cupoMujeres;
	}

	public String getCuartos() {
		return cuartos;
	}

	public void setCuartos(String cuartos) {
		this.cuartos = cuartos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
