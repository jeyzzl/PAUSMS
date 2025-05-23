// Bean del Catalogo de Edificios
package  aca.catalogo.spring;

public class CatEdificio{
	private String edificioId;	
	private String nombreEdificio;
	private String usuarios;

	public CatEdificio(){
		edificioId 		= "0";
		nombreEdificio	= "-";
		usuarios		= "-";
	}

	public String getEdificioId(){
		return edificioId;
	}	
	public void setEdificioId( String edificioId){
		this.edificioId = edificioId;
	}
	
	public String getNombreEdificio(){
		return nombreEdificio;
	}	
	public void setNombreEdificio( String nombreEdificio){
		this.nombreEdificio = nombreEdificio;
	}
	
	public String getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}
}