package aca.bitacora.spring;

public class BitEstado {
	
	private String estado;
	private String estadoNombre; 
	
	public BitEstado(){
		estado 			= "";
		estadoNombre 	= "";
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoNombre() {
		return estadoNombre;
	}

	public void setEstado_nombre(String estado_nombre) {
		this.estadoNombre = estado_nombre;
	}
	
}
