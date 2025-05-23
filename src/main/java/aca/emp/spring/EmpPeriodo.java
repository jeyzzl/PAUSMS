// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpPeriodo{
	private String periodoId;
	private String periodoNombre;
	private String fechaIni;
	private String fechaFin;
	private String estado;	
	
	public EmpPeriodo(){
		periodoId			= "";
		periodoNombre		= "";
		fechaIni			= "";
		fechaFin			= "";
		estado				= "";		
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}