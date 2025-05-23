// Bean del Catalogo de Descuentos
package  aca.catalogo.spring;

public class CatDescuento{
	private String descuentoId;	
	private String descuentoNombre;
	private String usuarios;
	
	public CatDescuento(){
		descuentoId 		= "0";
		descuentoNombre		= "";
		usuarios			= "";
	}
	
	public String getDescuentoId() {
		return descuentoId;
	}

	public void setDescuentoId(String descuentoId) {
		this.descuentoId = descuentoId;
	}

	public String getDescuentoNombre() {
		return descuentoNombre;
	}

	public void setDescuentoNombre(String descuentoNombre) {
		this.descuentoNombre = descuentoNombre;
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}
	
}