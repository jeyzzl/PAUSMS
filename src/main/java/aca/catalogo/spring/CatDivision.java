// Bean del Catalogo de Divisiones
package  aca.catalogo.spring;

public class CatDivision{
	private String divisionId;
	private String nombreDivision;
	private String direccion;
	private String colonia;
	private String codPostal;
	private String telefono;
	private String fax;
	private String email;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String nombreCorto;
	
	public CatDivision(){
		divisionId		= "";
		nombreDivision	= "";
		direccion		= "";
		colonia			= "";
		codPostal		= "";
		telefono		= "";
		fax				= "";
		email			= "";
		paisId			= "";
		estadoId		= "";
		ciudadId		= "";
	}
	
	public String getDivisionId(){
		return divisionId;
	}
	
	public void setDivisionId( String divisionId){
		this.divisionId = divisionId;
	}	
	
	public String getNombreDivision(){
		return nombreDivision;
	}
	
	public void setNombreDivision( String nombreDivision){
		this.nombreDivision = nombreDivision;
	}	
	
	public String getDireccion(){
		return direccion;
	}
	
	public void setDireccion( String direccion){
		this.direccion = direccion;
	}
	
	public String getColonia(){
		return colonia;
	}
	
	public void setColonia( String colonia){
		this.colonia = colonia;
	}
	
	public String getCodPostal(){
		return codPostal;
	}
	
	public void setCodPostal( String codPostal){
		this.codPostal = codPostal;
	}
	
	public String getTelefono(){
		return telefono;
	}
	
	public void setTelefono( String telefono){
		this.telefono = telefono;
	}
	
	public String getFax(){
		return fax;
	}
	
	public void setFax( String fax){
		this.fax = fax;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail( String email){
		this.email = email;
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public void setCiudadId( String ciudadId){
		this.ciudadId = ciudadId;
	}
	
	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto){
		this.nombreCorto = nombreCorto;
	}

}