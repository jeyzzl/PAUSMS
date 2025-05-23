// Bean del Catalogo de Extension
package  aca.catalogo.spring;


public class CatExtension{	
	
	private String extensionId;	
	private String nombreExtension;	
	private String referente;	
	private String direccion;	
	private String colonia;	
	private String codPostal;	
	private String telefono;	
	private String fax;	
	private String email;
	
	public CatExtension(){
		extensionId 		= "0";
		nombreExtension		= "-";
		referente			= "-";
		direccion			= "-";
		colonia				= "-";
		codPostal			= "-";
		telefono			= "-";
		fax					= "-";
		email				= "-";
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getNombreExtension() {
		return nombreExtension;
	}

	public void setNombreExtension(String nombreExtension) {
		this.nombreExtension = nombreExtension;
	}

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}