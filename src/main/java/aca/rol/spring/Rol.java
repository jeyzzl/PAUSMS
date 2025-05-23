// Bean del Catalogo de Roles
package  aca.rol.spring;

public class Rol{
	private String rolId;	
	private String rolNombre;
	
	public Rol(){
		rolId 		= "0";
		rolNombre	= "-";
	}

	public String getRolId() {
		return rolId;
	}

	public void setRolId(String rolId) {
		this.rolId = rolId;
	}

	public String getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(String rolNombre) {
		this.rolNombre = rolNombre;
	}	
	
}