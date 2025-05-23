package aca.rotaciones.spring;

public class RotInstitucion {

	private String institucionId;
	private String institucionNombre;
	
	public RotInstitucion(){		
		institucionId		= "";
		institucionNombre	= "";
	}
	
	public String getInstitucionId() {
		return institucionId;
	}

	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}

	public String getInstitucionNombre() {
		return institucionNombre;
	}

	public void setInstitucionNombre(String institucionNombre) {
		this.institucionNombre = institucionNombre;
	}
}
