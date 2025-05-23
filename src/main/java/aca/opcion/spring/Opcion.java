package aca.opcion.spring;

public class Opcion {
	private String codigoPersonal;
	private String alertaSesion;
	private String menuClick;
	
	public Opcion(){
		codigoPersonal	= "0";
		alertaSesion	= "N";
		menuClick		= "N";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getAlertaSesion() {
		return alertaSesion;
	}

	public void setAlertaSesion(String alertaSesion) {
		this.alertaSesion = alertaSesion;
	}

	public String getMenuClick() {
		return menuClick;
	}

	public void setMenuClick(String menuClick) {
		this.menuClick = menuClick;
	}
}