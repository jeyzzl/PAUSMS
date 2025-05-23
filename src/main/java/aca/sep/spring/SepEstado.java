package aca.sep.spring;

public class SepEstado {

	private String Estado_SE;
	private String Nombre;
	
	public SepEstado() {
		setEstado_SE("0");
		setNombre("-");
	}

	public String getEstado_SE() {
		return Estado_SE;
	}

	public void setEstado_SE(String estado_SE) {
		Estado_SE = estado_SE;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
