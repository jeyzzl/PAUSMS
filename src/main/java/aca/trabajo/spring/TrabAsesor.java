package aca.trabajo.spring;

public class TrabAsesor {
	private String codigoPersonal;
	private String deptId;
	private String fecha;
	private String status;
	
	public TrabAsesor() {
		codigoPersonal 	= "0";
		deptId 			= "0";
		fecha 			= null;
		status 			= "A";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
