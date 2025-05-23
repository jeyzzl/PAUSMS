// Bean del Catalogo de Materias de los grupos
package  aca.carga.spring;

public class CargaPermiso{
	private String cargaId;
	private String carreraId;
	private String recuperacion;
	private String usuario;
	
	public CargaPermiso(){
		cargaId			= "0";
		carreraId		= "0";
		recuperacion	= "N";
		usuario			= "0"; 
	}	
	
	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getRecuperacion() {
		return recuperacion;
	}

	public void setRecuperacion(String recuperacion) {
		this.recuperacion = recuperacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}