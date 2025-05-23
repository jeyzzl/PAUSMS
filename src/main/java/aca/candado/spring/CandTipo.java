// Clase para la tabla de Modulo
package aca.candado.spring;

public class CandTipo{
	private String tipoId;
	private String nombreTipo;
	private String responsable;	
	private String lugar;
	private String telefono;
	private String estado;
	private String correo;
	private String celular;
	private String persona;
	
	// Constructor
	public CandTipo(){		
		tipoId			= "0";
		nombreTipo		= "";
		responsable		= "";
		lugar			= "";
		telefono		= "";
		estado			= "A";
		correo			= "";
		celular			= "";
		persona			= "";
	}
			
	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}
	
}