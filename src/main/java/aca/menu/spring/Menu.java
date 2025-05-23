package aca.menu.spring;

public class Menu{
	private String menuId;
	private String menuNombre;
	private String nombreIngles;
	private String orden;
	
	public Menu(){
		menuId			= "";
		menuNombre		= "";
		nombreIngles	= "";
		orden			= "0";
	}

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNombre() {
		return menuNombre;
	}
	public void setMenuNombre(String menuNombre) {
		this.menuNombre = menuNombre;
	}

	public String getNombreIngles() {
		return nombreIngles;
	}
	public void setNombreIngles(String nombreIngles) {
		this.nombreIngles = nombreIngles;
	}

	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}	
}