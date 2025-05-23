package aca.residencia.spring;

public class ResAlumno {
	private String matricula;
	private String folio;
	private String calle;
	private String colonia;
	private String municipio;
	private String telArea;
	private String telNum;
	private String tutNombre;
	private String tutApellidos;
	private String razon;
	private String usuario;
	private String fecha;
	private String numero;
	private String periodoId;
	private String esPermanente;
	private String residenciaId;
	private String dormitorio;
	private String fechaInicio;
	private String fechaFinal;
	
	public ResAlumno(){
		matricula 		= "0";
		folio 			= "1";
		calle 			= "-";
		colonia 		= "-";
		municipio 		= "-";
		telArea 		= "-";
		telNum 			= "-";
		tutNombre 		= "-";
		tutApellidos 	= "-";
		razon 			= "0";
		usuario 		= "0";
		fecha 			= "-";
		numero 			= "0";
		periodoId 		= "0";
		esPermanente 	= "0";
		residenciaId 	= "0";
		dormitorio 		= "0";
		fechaInicio		= "";
		fechaFinal		= "";		
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getTelArea() {
		return telArea;
	}

	public void setTelArea(String telArea) {
		this.telArea = telArea;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getTutNombre() {
		return tutNombre;
	}

	public void setTutNombre(String tutNombre) {
		this.tutNombre = tutNombre;
	}

	public String getTutApellidos() {
		return tutApellidos;
	}

	public void setTutApellidos(String tutApellidos) {
		this.tutApellidos = tutApellidos;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getEsPermanente() {
		return esPermanente;
	}

	public void setEsPermanente(String esPermanente) {
		this.esPermanente = esPermanente;
	}

	public String getResidenciaId() {
		return residenciaId;
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

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}