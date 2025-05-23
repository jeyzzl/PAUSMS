package aca.catalogo.spring;

public class CatHorarioFacultad {
	private String horarioId;
	private String periodo;
	private String turno;
	private String horaInicio;
	private String horaFinal;
	private String minutoInicio;
	private String minutoFinal;
	private String nombre;
	private String tipo;
		
	public CatHorarioFacultad(){
		horarioId		= "0";
		periodo			= "0";
		turno			= "M";
		horaInicio		= "";
		horaFinal		= "";
		minutoInicio	= "";
		minutoFinal		= "";
		nombre			= "-";
		tipo			= "1";
	}
	
	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(String minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public String getMinutoFinal() {
		return minutoFinal;
	}

	public void setMinutoFinal(String minutoFinal) {
		this.minutoFinal = minutoFinal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
