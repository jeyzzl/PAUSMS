package aca.alumno.spring;

public class AlumHermanos{	
	private String familiaId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	
	public AlumHermanos(){
		familiaId		= "";
		codigoPersonal	= "";
		fecha			= "";
		estado			= "";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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