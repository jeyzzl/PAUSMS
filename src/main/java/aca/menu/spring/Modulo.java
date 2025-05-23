package aca.menu.spring;

public class Modulo  implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private String moduloId;
	private String nombreModulo;
	private String url;
	private String icono;
	private String menuId;
	private String nombreIngles;
	
	// Constructor
	public Modulo(){		
		moduloId 		= "";
		nombreModulo	= "";
		url 			= "";
		icono			= "";
		menuId			= "";
		nombreIngles	= "";
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getModuloId(){
		return moduloId;
	}
	
	public void setModuloId(String moduloId){
		this.moduloId = moduloId;
	}
	
	public String getNombreModulo(){
		return nombreModulo;
	}
	
	public void setNombreModulo(String nombreModulo){
		this.nombreModulo = nombreModulo;
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

	public String getNombreIngles() {
		return nombreIngles;
	}

	public void setNombreIngles(String nombreIngles) {
		this.nombreIngles = nombreIngles;
	}	
		
}