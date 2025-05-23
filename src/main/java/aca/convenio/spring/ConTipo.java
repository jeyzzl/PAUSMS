package aca.convenio.spring;

public class ConTipo{
	
	private String tipoId;
	private String tipoNombre;
			
	public ConTipo(){
		tipoId 		= "0";
		tipoNombre	= "-";
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}
}