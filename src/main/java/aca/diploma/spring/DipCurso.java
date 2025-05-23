package  aca.diploma.spring;

public class DipCurso{
	private String diplomaId;
	private String institucion;
	private String curso;
	private String tema;
	private String horas;
	private String periodo;
	private String fecha;
	private String firmaUno;
	private String firmaDos;

	
	public DipCurso(){
		diplomaId		= "0";
		institucion		= "-";
		curso			= "-";
		tema			= "-";
		horas 			= "-";
		periodo			= "-";
		fecha			= "-";
		firmaUno		= "-";
		firmaDos		= "-";
	}


	public String getDiplomaId() {
		return diplomaId;
	}
	public void setDiplomaId(String diplomaId) {
		this.diplomaId = diplomaId;
	}

	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFirmaUno() {
		return firmaUno;
	}

	public void setFirmaUno(String firmaUno) {
		this.firmaUno = firmaUno;
	}

	public String getFirmaDos() {
		return firmaDos;
	}

	public void setFirmaDos(String firmaDos) {
		this.firmaDos = firmaDos;
	}
}