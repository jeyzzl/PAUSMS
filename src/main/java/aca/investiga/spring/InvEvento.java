package aca.investiga.spring;

public class InvEvento {
	private String codigoPersonal;
	private String folio;
	private String proyectoId;
	private String fechaSolicitud;
	private String fechaInicio;
	private String lugar;
	private String tipoEvento;
	private String dias;
	private String nombreEvento;
	private String participa;
	private String tipoBeca;
	private String alumnos;
	private String hospedaje;
	private String transporte;
	private String viaticos;
	private String gastos;
	private String descripcion;
	private String estado;

	public InvEvento(){
		codigoPersonal		= "";
		folio				= "";
		proyectoId			= "";
		fechaSolicitud		= "";
		fechaInicio			= "";
		lugar				= "";
		tipoEvento			= "";
		dias				= "";
		nombreEvento		= "";
		participa			= "";
		tipoBeca			= "";
		alumnos				= "";
		hospedaje			= "";
		transporte			= "";
		viaticos			= "";
		gastos				= "";
		descripcion			= "";
		estado				= "";
	}
		
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getParticipa() {
		return participa;
	}

	public void setParticipa(String participa) {
		this.participa = participa;
	}

	public String getTipoBeca() {
		return tipoBeca;
	}

	public void setTipoBeca(String tipoBeca) {
		this.tipoBeca = tipoBeca;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
	}

	public String getHospedaje() {
		return hospedaje;
	}

	public void setHospedaje(String hospedaje) {
		this.hospedaje = hospedaje;
	}

	public String getTransporte() {
		return transporte;
	}

	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}

	public String getViaticos() {
		return viaticos;
	}

	public void setViaticos(String viaticos) {
		this.viaticos = viaticos;
	}

	public String getGastos() {
		return gastos;
	}

	public void setGastos(String gastos) {
		this.gastos = gastos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}