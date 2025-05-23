package aca.alumno.spring;

public class AlumRemedial {
	
	private String codigoPersonal;
	private String cursoId;
	private String estado;	
	private String fecha;	
	
	public AlumRemedial(){
		codigoPersonal	= "0";
		cursoId 		= "0";
		estado			= "A";		
		fecha			= "";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
