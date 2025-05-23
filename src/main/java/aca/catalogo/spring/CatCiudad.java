// Bean del Catalogo de Ciudades
package  aca.catalogo.spring;

public class CatCiudad{
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String nombreCiudad;
	
	public CatCiudad(){
		paisId 			= "0";
		estadoId 		= "0";
		ciudadId		= "0";
		nombreCiudad	= "-";
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
	
	public String getNombreCiudad(){
		return nombreCiudad;
	}
	
	public void setNombreCiudad( String nombreCiudad){
		this.nombreCiudad = nombreCiudad;
	}
}