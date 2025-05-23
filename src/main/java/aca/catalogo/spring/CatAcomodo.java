package  aca.catalogo.spring;

public class CatAcomodo{
	private String acomodoId;	
	private String nombre;
	private String nombreCorto;
	private String tipo;
	
	public CatAcomodo(){
		acomodoId 			= "0";
		nombre 				= "";
		nombreCorto 		= "";
		tipo 				= "";
	}

	public String getAcomodoId() {
		return acomodoId;
	}

	public void setAcomodoId(String acomodoId) {
		this.acomodoId = acomodoId;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
}