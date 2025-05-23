// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpHoras{
	private String codigoPersonal;
	private String folio;
	private String cursoId;
	private String cargaId;
	private String frecuencia;
	private String semanas;
	private String precio;
	private String materia;
	private String carreraId;
	private String fechaIni;
	private String fechaFin;
	private String estado;	
	private String fecha;
	private String tipo;
	private String contratoId;
	private String tipoPagoId;
	private String bloqueId;
	
	public EmpHoras(){
		codigoPersonal		= "0";
		folio				= "0";
		cargaId				= "000000";
		cursoId				= "000000000000000";
		frecuencia			= "0";
		semanas				= "0";
		precio				= "0";
		materia				= "-";
		carreraId			= "00000";
		fechaIni			= aca.util.Fecha.getHoy();
		fechaFin			= aca.util.Fecha.getHoy();
		estado				= "S";
		fecha				= "01/01/2000";
		tipo 				= "O";
		contratoId			= "0"; 
		tipoPagoId			= "1";
		bloqueId			= "0";
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

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getSemanas() {
		return semanas;
	}

	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
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

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContratoId() {
		return contratoId;
	}

	public void setContratoId(String contratoId) {
		this.contratoId = contratoId;
	}

	public String getTipoPagoId() {
		return tipoPagoId;
	}

	public void setTipoPagoId(String tipoPagoId) {
		this.tipoPagoId = tipoPagoId;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}
	
}