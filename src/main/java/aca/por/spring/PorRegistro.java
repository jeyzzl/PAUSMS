// Bean del Catalogo Curso
package  aca.por.spring;

public class PorRegistro{
	
	private String codigoPersonal;
	private String equipoId;
	private String estado;
	
	public PorRegistro(){
		codigoPersonal			= "";
		equipoId				= "";
		estado 					= "A";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getEquipoId() {
		return equipoId;
	}

	public void setEquipoId(String equipoId) {
		this.equipoId = equipoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}