package aca.leg.spring;

public class LegEstado {

	public String estadoId;
	public String estadoNombre;
	
	public LegEstado(){
		estadoId 		= "";
		estadoNombre	= "";
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getEstadoNombre() {
		return estadoNombre;
	}

	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}
	
}