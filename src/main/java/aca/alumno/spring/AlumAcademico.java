// Bean de datos academicos del alumno
package  aca.alumno.spring;

public class AlumAcademico{
	private String codigoPersonal;
	private String tipoAlumno;
	private String clasFin;
	private String obrero;
	private String obreroInstitucion;
	private String residenciaId;
	private String dormitorio;
	private String fSolicitud;
	private String fAdmision;
	private String fAlta;
	private String modalidadId;
	private String extensionId;
	private String grado;
	private String semestre; 
	private String hoStatus;
	private String prepaLugar;
	private String requerido;
	private String nivelInicioId;
	private String acomodoId;
	
	public AlumAcademico(){
		codigoPersonal		= "0";
		tipoAlumno			= "1";
		clasFin				= "1";
		obrero				= "N";
		obreroInstitucion	= "0";
		residenciaId		= "I";
		dormitorio			= "0";
		fSolicitud			= "";
		fAdmision			= "";
		fAlta				= "";
		modalidadId 		= "1";
		extensionId			= "0";
		grado 				= "0";
		semestre			= "0";
		hoStatus			= "I";
		prepaLugar			= "0";
		requerido 			= "N";
		nivelInicioId 		= "0";
		acomodoId			= "0";
	}
	
	public String getClasFin() {
		return clasFin;
	}
	
	public void setClasFin(String clasFin) {
		this.clasFin = clasFin;
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getDormitorio() {
		return dormitorio;
	}
	
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
	public String getExtensionId() {
		return extensionId;
	}
	
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
	
	public String getFAdmision() {
		return fAdmision;
	}
	
	public void setFAdmision(String admision) {
		fAdmision = admision;
	}
	
	public String getFAlta() {
		return fAlta;
	}
	
	public void setFAlta(String alta) {
		fAlta = alta;
	}
	
	public String getFSolicitud() {
		return fSolicitud;
	}
	
	public void setFSolicitud(String solicitud) {
		fSolicitud = solicitud;
	}
	
	public String getModalidadId() {
		return modalidadId;
	}
	
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}
	
	public String getObrero() {
		return obrero;
	}
	
	public void setObrero(String obrero) {
		this.obrero = obrero;
	}
	
	public String getObreroInstitucion() {
		return obreroInstitucion;
	}
	
	public void setObreroInstitucion(String obreroInstitucion) {
		this.obreroInstitucion = obreroInstitucion;
	}
	
	public String getResidenciaId() {
		return residenciaId;
	}
	
	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}
	
	public String getTipoAlumno() {
		return tipoAlumno;
	}
	
	public void setTipoAlumno(String tipoAlumno) {
		this.tipoAlumno = tipoAlumno;
	}	
	
	public String getPrepaLugar() {
		return prepaLugar;
	}

	public void setPrepaLugar(String prepaLugar) {
		this.prepaLugar = prepaLugar;
	}

	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getHoStatus() {
		return hoStatus;
	}

	public void setHoStatus(String hoStatus) {
		this.hoStatus = hoStatus;
	}

	public String getNivelInicioId() {
		return nivelInicioId;
	}
	public void setNivelInicioId(String nivelInicioId) {
		this.nivelInicioId = nivelInicioId;
	}

	public String getAcomodoId() {
		return acomodoId;
	}
	public void setAcomodoId(String acomodoId) {
		this.acomodoId = acomodoId;
	}

	
}