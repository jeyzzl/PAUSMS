//Bean del periodo de vacaciones del alumno
package aca.alumno.spring;

public class AlumColor {
	private String codigoPersonal;
	private String color;
	private String menu;
	private String reloj;
	private String colorReloj;
	
	public AlumColor(){
		codigoPersonal	= "0";
		color 			= "default";
		menu			= "default";
		reloj			= "default";
		colorReloj		= "default";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getReloj() {
		return reloj;
	}
	public void setReloj(String reloj) {
		this.reloj = reloj;
	}
	public String getColorReloj() {
		return colorReloj;
	}
	public void setColorReloj(String colorReloj) {
		this.colorReloj = colorReloj;
	}	
}