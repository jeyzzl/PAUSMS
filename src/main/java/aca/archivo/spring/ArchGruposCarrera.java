package aca.archivo.spring;

public class ArchGruposCarrera {
    private String carrera;
	private String grupos;
	
	public ArchGruposCarrera(){
		carrera = "0";
		grupos 	= "*";
	}
			
	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getGrupos() {
		return grupos;
	}

	public void setGrupos(String grupos) {
		this.grupos = grupos;
	}
	
}