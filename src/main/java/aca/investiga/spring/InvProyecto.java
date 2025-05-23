package aca.investiga.spring;

/**
 * @author etorres
 *
 */
public class InvProyecto {
	private String proyectoId;
	private String proyectoNombre;
	private String codigoPersonal;
	private String tipo;
	private String linea;
	private String carreraId;
	private String departamento;
	private String fechaInicio;
	private String fechaFinal;
	private String resumen;
	private String documento;
	private String estado;
	private String folio;
	private String antecedentes;
	private String justificacion;
	private String resDocente;
	private String resAlumno;	
	private String investigadores;
	private String estadoArte;
	private String tipoDocumento;
	
	public InvProyecto(){
		proyectoId			= "";
		codigoPersonal		= "";
		proyectoNombre		= "-";		
		tipo 				= "0";
		linea 				= "-";
		carreraId 			= "-";
		departamento 		= "-";
		fechaInicio 		= "";
		fechaFinal			= "";
		resumen 			= "-";
		estadoArte			= "";
		documento 			= "N";
		estado 				= "0";
		folio 				= "-";
		antecedentes		= "-";
		justificacion		= "-";
		resDocente			= "-";
		resAlumno			= "-";
		investigadores		= "-";
		tipoDocumento		= "";
	}	
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getProyectoNombre() {
		return proyectoNombre;
	}
	public void setProyectoNombre(String proyectoNombre) {
		this.proyectoNombre = proyectoNombre;
	}
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}	
	public String getEstadoArte() {
		return estadoArte;
	}
	public void setEstadoArte(String estadoArte) {
		this.estadoArte = estadoArte;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}	
	public String getResDocente() {
		return resDocente;
	}
	public void setResDocente(String resDocente) {
		this.resDocente = resDocente;
	}
	public String getResAlumno() {
		return resAlumno;
	}
	public void setResAlumno(String resAlumno) {
		this.resAlumno = resAlumno;
	}	
	public String getInvestigadores() {
		return investigadores;
	}
	public void setInvestigadores(String investigadores) {
		this.investigadores = investigadores;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}	
}