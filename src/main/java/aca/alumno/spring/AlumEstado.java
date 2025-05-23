// Bean de Estado del alumno en el procesos de matrícula( Por cada bloque).
package  aca.alumno.spring;

public class AlumEstado{
	
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String estado;	
	private String modalidadId;
	private String tipoalumnoId;
	private String facultadId;
	private String carreraId;
	private String planId;
	private String fecha;
	private String residenciaId;
	private String dormitorio;
	private String ciclo;
	private String grado;
	private String clasFin;
	
	public AlumEstado(){
		codigoPersonal		= "0";
		cargaId				= "0";
		bloqueId			= "0";
		estado				= "M";
		modalidadId			= "0";
		tipoalumnoId		= "0";
		facultadId			= "0";
		carreraId			= "0";
		planId				= "0";
		fecha				= aca.util.Fecha.getHoy();
		residenciaId		= "0";
		dormitorio			= "0";
		ciclo 				= "0";
		grado 				= "0";
		clasFin 			= "0"; 
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getCargaId(){
		return cargaId;
	}
	
	public void setCargaId( String Carga_id){
		this.cargaId = Carga_id;
	}	
	
	public String getBloqueId(){
		return bloqueId;
	}
	
	public void setBloqueId( String bloqueId){
		this.bloqueId = bloqueId;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado( String estado){
		this.estado = estado;
	}	
	
	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getTipoalumnoId() {
		return tipoalumnoId;
	}

	public void setTipoalumnoId(String tipoalumnoId) {
		this.tipoalumnoId = tipoalumnoId;
	}
	
	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
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

	public String getResidenciaId() {
		return residenciaId;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}

	public String getDormitorio() {
		return dormitorio;
	}
	
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}

	public String getCiclo() {
		return ciclo;
	}
	
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	
	public String getClasFin() {
		return clasFin;
	}

	public void setClasFin(String clasFin) {
		this.clasFin = clasFin;
	}

}