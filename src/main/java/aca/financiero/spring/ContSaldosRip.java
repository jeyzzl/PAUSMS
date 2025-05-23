package aca.financiero.spring;


public class ContSaldosRip {
	private String id;
	private String version;
	private String matricula;
	private String saldo;
	private String naturaleza;
	private String quienRegistro;
	private String cuandoRegistro;
	private String quienModifico;
	private String cuandoModifico;
	private String status;
	private String contabilidad;
	private String idEjercicio;
	private String observacion;
	
	public ContSaldosRip(){
		id							= "";	
		version						= "";
		matricula					= "";
		saldo						= "";
		naturaleza					= "";
		quienRegistro				= "";
		cuandoRegistro				= "";
		quienModifico				= "";
		cuandoModifico				= "";
		status			   			= "";
		contabilidad				= "";
		idEjercicio	   				= "";
		observacion					= "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}

	public String getQuienRegistro() {
		return quienRegistro;
	}

	public void setQuienRegistro(String quienRegistro) {
		this.quienRegistro = quienRegistro;
	}

	public String getCuandoRegistro() {
		return cuandoRegistro;
	}

	public void setCuandoRegistro(String cuandoRegistro) {
		this.cuandoRegistro = cuandoRegistro;
	}

	public String getQuienModifico() {
		return quienModifico;
	}

	public void setQuienModifico(String quienModifico) {
		this.quienModifico = quienModifico;
	}

	public String getCuandoModifico() {
		return cuandoModifico;
	}

	public void setCuandoModifico(String cuandoModifico) {
		this.cuandoModifico = cuandoModifico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContabilidad() {
		return contabilidad;
	}

	public void setContabilidad(String contabilidad) {
		this.contabilidad = contabilidad;
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}