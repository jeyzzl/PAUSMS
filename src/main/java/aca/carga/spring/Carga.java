// Bean del Catalogo Cargas
package  aca.carga.spring;

public class Carga{
	private String cargaId;
	private String nombreCarga;
	private String fCreada;
	private String periodo;
	private String ciclo;
	private String fInicio;
	private String fFinal;
	private String fExtra;
	private String numCursos;
	private String estado;
	private String tipoCarga;
	private String semanas;
	private String finServicios;
	private String evalua;
	private String orden;
	private String prioridad;
	private String iniInternado;
	private String finInternado;
	private String secuencia;
	private String bloqueo;
	
	public Carga(){
		cargaId			= "0";
		nombreCarga		= "-";
		fCreada			= aca.util.Fecha.getHoy();
		periodo			= "";
		ciclo			= "0";
		fInicio			= aca.util.Fecha.getHoy();
		fFinal			= aca.util.Fecha.getHoy();
		fExtra			= aca.util.Fecha.getHoy();
		numCursos		= "0";
		estado			= "A";
		tipoCarga		= "O";
		semanas			= "16";
		finServicios	= aca.util.Fecha.getHoy();
		evalua			= "S";
		orden 			= "1";
		prioridad		= "0";
		iniInternado	= aca.util.Fecha.getHoy();
		finInternado	= aca.util.Fecha.getHoy();
		secuencia		= "0";
		bloqueo			= "0";
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getNombreCarga() {
		return nombreCarga;
	}
	public void setNombreCarga(String nombreCarga) {
		this.nombreCarga = nombreCarga;
	}

	public String getFCreada() {
		return fCreada;
	}
	public void setFCreada(String fCreada) {
		this.fCreada = fCreada;
	}

	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	
	public String getFInicio() {
		return fInicio;
	}
	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getFFinal() {
		return fFinal;
	}
	public void setFFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getFExtra() {
		return fExtra;
	}
	public void setFExtra(String fExtra) {
		this.fExtra = fExtra;
	}	

	public String getNumCursos() {
		return numCursos;
	}
	public void setNumCursos(String numCursos) {
		this.numCursos = numCursos;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public String getSemanas() {
		return semanas;
	}
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	public String getFinServicios() {
		return finServicios;
	}
	public void setFinServicios(String finServicios) {
		this.finServicios = finServicios;
	}

	public String getEvalua() {
		return evalua;
	}
	public void setEvalua(String evalua) {
		this.evalua = evalua;
	}

	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getfCreada() {
		return fCreada;
	}
	public void setfCreada(String fCreada) {
		this.fCreada = fCreada;
	}

	public String getfInicio() {
		return fInicio;
	}
	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}
	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getfExtra() {
		return fExtra;
	}
	public void setfExtra(String fExtra) {
		this.fExtra = fExtra;
	}

	public String getIniInternado() {
		return iniInternado;
	}
	public void setIniInternado(String iniInternado) {
		this.iniInternado = iniInternado;
	}

	public String getFinInternado() {
		return finInternado;
	}
	public void setFinInternado(String finInternado) {
		this.finInternado = finInternado;
	}

	public String getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getBloqueo() {
		return bloqueo;
	}
	public void setBloqueo(String bloqueo) {
		this.bloqueo = bloqueo;
	}
	
}