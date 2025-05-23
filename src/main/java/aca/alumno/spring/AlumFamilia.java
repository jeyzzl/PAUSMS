package aca.alumno.spring;

public class AlumFamilia{	
	private String familiaId;
	private String fecha;
	private String estado;
	
	public AlumFamilia(){
		familiaId	= "";
		fecha		= "";
		estado		= "";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
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