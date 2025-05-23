package aca.alumno.spring;

public class AlumGradua {
	private String codigoPersonal;
	private String planId;
	private String fecha;
	private String fechaGraduacion;
	private String evento;
	private String avance;
	private String matAc;
	private String matIns;
	private String matPen;
	private String diploma;
	
	public AlumGradua(){
		codigoPersonal	= "";
		planId			= "";
		fecha			= "";
		fechaGraduacion	= "";
		evento			= "";
		avance			= "";
		matAc 			= "";
		matIns			= "";
		matPen			= "";
		diploma			= "";
		
	}
	
	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaGraduacion() {
		return fechaGraduacion;
	}

	public void setFechaGraduacion(String fechaGraduacion) {
		this.fechaGraduacion = fechaGraduacion;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public String getMatAc() {
		return matAc;
	}

	public void setMatAc(String matAc) {
		this.matAc = matAc;
	}

	public String getMatIns() {
		return matIns;
	}

	public void setMatIns(String matIns) {
		this.matIns = matIns;
	}

	public String getMatPen() {
		return matPen;
	}

	public void setMatPen(String matPen) {
		this.matPen = matPen;
	}
	
}