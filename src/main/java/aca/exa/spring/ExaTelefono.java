package  aca.exa.spring;

public class ExaTelefono{
	private String telefonoId;	
	private String matricula;
	private String tipo;
	private String telefono;	
	private String fechaAct;
	private String eliminado;
	private String idTelefono;
	
	public ExaTelefono(){
		telefonoId 			= "";
		matricula			= "";
		tipo 				= "";
		telefono			= "";
		fechaAct			= "";
		eliminado			= "";
		idTelefono			= "";
	}
	

	public String getTelefonoId() {
		return telefonoId;
	}


	public void setTelefonoId(String telefonoId) {
		this.telefonoId = telefonoId;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getFechaAct() {
		return fechaAct;
	}


	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}


	public String getEliminado() {
		return eliminado;
	}


	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}


	public String getIdTelefono() {
		return idTelefono;
	}


	public void setIdTelefono(String idTelefono) {
		this.idTelefono = idTelefono;
	}
	
}