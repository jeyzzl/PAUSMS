// Bean de documentos de admision del alumno
package  aca.admision.spring;

public class AdmProceso{
	private String folio;
	private String fechaRegistro;	
	private String fechaSolicitud;
	private String fechaDocumentos;
	private String fechaAdmision;
	private String fechaCarta;
	private String fechaExamen;
	private String fechaDirecto;
	
	
	public AdmProceso(){
		folio 				= "";
		fechaRegistro 		= "";
		fechaSolicitud		= "";
		fechaDocumentos		= "";
		fechaAdmision		= "";
		fechaCarta			= "";
		fechaExamen			= "";
		fechaDirecto		= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaDocumentos() {
		return fechaDocumentos;
	}

	public void setFechaDocumentos(String fechaDocumentos) {
		this.fechaDocumentos = fechaDocumentos;
	}

	public String getFechaAdmision() {
		return fechaAdmision;
	}

	public void setFechaAdmision(String fechaAdmision) {
		this.fechaAdmision = fechaAdmision;
	}

	public String getFechaCarta() {
		return fechaCarta;
	}

	public void setFechaCarta(String fechaCarta) {
		this.fechaCarta = fechaCarta;
	}

	public String getFechaExamen() {
		return fechaExamen;
	}

	public void setFechaExamen(String fechaExamen) {
		this.fechaExamen = fechaExamen;
	}

	public String getFechaDirecto() {
		return fechaDirecto;
	}

	public void setFechaDirecto(String fechaDirecto) {
		this.fechaDirecto = fechaDirecto;
	}
	
}