package aca.trabajo.spring;

public class TrabAlum {
	private String deptId;
	private String catId;
	private String matricula;
	private String periodoId;
	private String horas;
	private String estado;
	private String pago;
	
	public TrabAlum() {
		deptId 		= "0";
		catId 		= "0";
		matricula 	= "0";
		periodoId 	= "0";
		horas 		= "0";
		estado 		= "A";
		pago		= "0";
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}
	
	
}
