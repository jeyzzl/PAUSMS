package aca.trabajo.spring;

public class TrabInformeAlum {
	private String matricula;
	private String informeId;
	private String deptId;
	private String catId;
	private String fecha;
	private String usuario;
	private String horas;
	private String periodoId;
	private String status;
	private String horaInicio;
	private String horaFin;
	private String descripcion;
	
	public TrabInformeAlum() {
		setMatricula("0");
		setInformeId("0");
		setDeptId("0");
		setCatId("0");
		setFecha(null);
		setUsuario("0");
		setHoras("0");
		setPeriodoId("1");
		setStatus("P");
		setHoraInicio(null);
		setHoraFin(null);
		setDescripcion("");
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getInformeId() {
		return informeId;
	}

	public void setInformeId(String informeId) {
		this.informeId = informeId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
