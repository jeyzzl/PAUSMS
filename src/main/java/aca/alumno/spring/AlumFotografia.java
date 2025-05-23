package  aca.alumno.spring;

public class AlumFotografia{
	private String codigoPersonal;
	private String resolucion;
		
	public AlumFotografia(){
		codigoPersonal	= "";
		resolucion		= "";	
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
}