package aca.tit.spring;

public class TitInstitucionUsuario {

	private String institucion;
    private String usuario;
    
    public TitInstitucionUsuario() {
    	institucion		= "";
    	usuario			= "";
        
    }

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
 
}
