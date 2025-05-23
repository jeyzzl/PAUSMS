// Bean del Catalogo de Ubicaciones de Recogida
package  aca.catalogo.spring;

public class CatRecogida{
	private String recogidaId;	
	private String nombre;
	private String nombreCorto;
	
	public CatRecogida(){
		recogidaId 			= "0";
		nombre 				= "";
		nombreCorto 		= "";
	}

	public String getRecogidaId() {
		return recogidaId;
	}
	public void setRecogidaId(String recogidaId) {
		this.recogidaId = recogidaId;
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
	
}