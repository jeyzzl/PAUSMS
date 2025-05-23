// Clase para la tabla de Modulo_opcion
package aca.menu.spring;

public class Opcion  implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private String moduloId;
	private String opcionId;
	private String nombreOpcion;
	private String url;
	private String icono;
	private String orden;
	private String usuarios;
	private String carpeta;
	
	// Constructor
	public Opcion(){
		moduloId 		= "";
		opcionId		= "";
		nombreOpcion	= "";
		url 			= "";
		icono			= "";
		orden 			= "";
		usuarios		= "";
		carpeta			= "/";
	}
	
	public String getModuloId(){
		return moduloId;
	}
	
	public void setModuloId(String moduloId){
		this.moduloId = moduloId;
	}

	public String getOpcionId(){
		return opcionId;
	}

	public void setOpcionId(String opcionId){
		this.opcionId = opcionId;
	}
	
	public String getNombreOpcion(){
		return nombreOpcion;
	}
	
	public void setNombreOpcion(String nombreOpcion){
		this.nombreOpcion = nombreOpcion;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getIcono(){
		return icono;
	}
	
	public void setIcono(String icono){
		this.icono = icono;
	}
	
	public String getOrden(){
		return orden;
	}
	
	public void setOrden(String orden){
		this.orden = orden;
	}
	
	public String getUsuarios(){
		return usuarios;
	}
	
	public void setUsuarios(String usuarios){
		this.usuarios = usuarios;
	}	
	
	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}	

}