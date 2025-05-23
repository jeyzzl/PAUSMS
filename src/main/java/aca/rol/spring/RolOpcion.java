// Bean del Catalogo de Roles
package  aca.rol.spring;

public class RolOpcion{
	private String rolId;	
	private String opcionId;
	
	public RolOpcion(){
		rolId 		= "";
		opcionId	= "";
	}
	
	public String getRolId() {
		return rolId;
	}

	public void setRolId(String rolId) {
		this.rolId = rolId;
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

}