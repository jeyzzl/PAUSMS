package aca.alumno.spring;

public class AlumGraduaMat {
	private String codigoPersonal;
	private String cursoId;
	private String programada;
	private String comentario;
	
	public AlumGraduaMat(){
		codigoPersonal	= "";
		cursoId     	= "";
		programada		= "";
		comentario		= "";
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

	public String getProgramada() {
		return programada;
	}

	public void setProgramada(String programada) {
		this.programada = programada;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}