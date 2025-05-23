package aca.admision.spring;

public class AdmOpciones {
	private String opcionId;
	private String nombre;
		
	public AdmOpciones(){
		opcionId 	= "0";
		nombre 		= "";
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}