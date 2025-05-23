// Clase para la tabla de Modulo
package aca.menu.spring;

public class ModuloJsp{
	
	private int Id;
	private String ruta;
	private String enoc;
	private String atlas;
	private String rutaCorta;
	
	// Constructor
	public ModuloJsp(){		
		Id 			= 0;
		ruta		= "";
		enoc 		= "";
		atlas		= "";
		rutaCorta	= "";
	}
	
	// Constructor
	public ModuloJsp( int id, String ruta, String enoc, String atlas, String rutaCorta){
		this.Id 		= id;
		this.ruta		= ruta;
		this.enoc 		= enoc;
		this.atlas		= atlas;
		this.rutaCorta	= rutaCorta;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getEnoc() {
		return enoc;
	}

	public void setEnoc(String enoc) {
		this.enoc = enoc;
	}

	public String getAtlas() {
		return atlas;
	}

	public void setAtlas(String atlas) {
		this.atlas = atlas;
	}

	public String getRutaCorta() {
		return rutaCorta;
	}

	public void setRutaCorta(String rutaCorta) {
		this.rutaCorta = rutaCorta;
	}
	
}