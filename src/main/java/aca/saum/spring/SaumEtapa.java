// Bean del Catalogo de Roles
package  aca.saum.spring;

public class SaumEtapa{
	private String id;
	private String version;
	private String nombre;
	private String procedimiento;
	private String recetaId;
	
	public SaumEtapa(){
		id 				= "";
		version			= "";
		nombre			= "";
		procedimiento	= "-";
		recetaId		= "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getRecetaId() {
		return recetaId;
	}

	public void setRecetaId(String recetaId) {
		this.recetaId = recetaId;
	}
	
}