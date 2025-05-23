 // Bean de datos personales del padre 
 package  aca.padre.spring;
 
 public class PadrePersonal{
 	private String padreId;
 	private String nombre;
 	private String paterno;
 	private String materno; 	
 	private String correo;
 	private String telefono;
 	private String tipo;
 	
 	public PadrePersonal(){
 		padreId				= "";
 		nombre				= "";
 		paterno				= "";
 		materno				= ""; 		
 		correo				= ""; 		
 		telefono			= "";
 		tipo				= "";
 	} 	

	public String getPadreId() {
		return padreId;
	}

	public void setPadreId(String padreId) {
		this.padreId = padreId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
 }