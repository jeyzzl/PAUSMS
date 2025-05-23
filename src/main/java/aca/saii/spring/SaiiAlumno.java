package aca.saii.spring;

public class SaiiAlumno {
	
	private String folio;
	private String plantel;
	private String planSep;
	private String ciclo;
	private String curp;
	private String nombre;
	private String paterno;
	private String materno;
	private String fecha;
	private String codigoPersonal;
	private String planUm;
	private String genero;
	private String edad;
	private String grado;
	private String paisId;
	private String estadoId;
	private String prepaLugar;
	private String usado;
	
	public SaiiAlumno() {
		folio 			= "0";
		plantel 		= "";
		planSep 		= "";
		ciclo 			= "0";
		curp 			= "";
		nombre 			= "";
		paterno 		= "";
		materno 		= "";
		fecha 			= "";
		codigoPersonal  = "";
		planUm 			= "";
		genero 			= "";
		edad 			= "0";
		grado 			= "0";
		paisId 			= "0";
		estadoId 		= "0";
		prepaLugar	 	= "0";
		usado 			= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPlantel() {
		return plantel;
	}

	public void setPlantel(String plantel) {
		this.plantel = plantel;
	}

	public String getPlanSep() {
		return planSep;
	}

	public void setPlanSep(String planSep) {
		this.planSep = planSep;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanUm() {
		return planUm;
	}

	public void setPlanUm(String planUm) {
		this.planUm = planUm;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getPrepaLugar() {
		return prepaLugar;
	}

	public void setPrepaLugar(String prepaLugar) {
		this.prepaLugar = prepaLugar;
	}

	public String getUsado() {
		return usado;
	}

	public void setUsado(String usado) {
		this.usado = usado;
	}	
}
