// Clase para la tabla de Modulo
package aca.menu.spring;

public class ConJsp{
	
	private String ruta;
	private String abrir;
	private String cerrar;
	
	// Constructor
	public ConJsp(){		
		ruta		= "";
		abrir 		= "0";
		cerrar		= "0";		
	}
	
	// Constructor
	public ConJsp( String ruta, String abrir, String cerrar){		
		this.ruta		= ruta;
		this.abrir 		= abrir;
		this.cerrar		= cerrar;		
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getAbrir() {
		return abrir;
	}

	public void setAbrir(String abrir) {
		this.abrir = abrir;
	}

	public String getCerrar() {
		return cerrar;
	}

	public void setCerrar(String cerrar) {
		this.cerrar = cerrar;
	}	
}