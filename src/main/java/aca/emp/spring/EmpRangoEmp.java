// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpRangoEmp{
	private String codigoPersonal;
	private String cargaId;
	private String rangoId;
	private String fecha;
	private String estado;	
	
	public EmpRangoEmp(){
		codigoPersonal 	= "0";
		cargaId			= "000000";
		rangoId 		= "-";
		fecha 			= aca.util.Fecha.getHoy();
		estado 			= "A";	
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getRangoId() {
		return rangoId;
	}

	public void setRangoId(String rangoId) {
		this.rangoId = rangoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}