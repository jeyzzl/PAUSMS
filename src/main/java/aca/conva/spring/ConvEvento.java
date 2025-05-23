package aca.conva.spring;

public class ConvEvento{
	
	private String convalidacionId;
	private String fecha;
	private String usuario;
	private String codigoPersonal;
	private String planId;	
	private String estado;
	private String comentario;
	private String institucion;
	private String programa;
	private String tipo;
	private String dictamen;
	private String tipoConv;
	private String periodo;
	private String planOrigen;
			
	public ConvEvento(){
		convalidacionId 	= "";
		fecha				= "";
		usuario				= "";
		codigoPersonal		= "";
		planId				= "";
		estado				= "";
		comentario			= "";
		institucion			= "x";
		programa			= "x";
		tipo				= "-";
		dictamen			= "-";
		tipoConv			= "-";
		periodo				= "-";
		planOrigen				= "-";
	}	

	public String getConvalidacionId() {
		return convalidacionId;
	}

	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDictamen() {
		return dictamen;
	}

	public void setDictamen(String dictamen) {
		this.dictamen = dictamen;
	}

	public String getTipoConv() {
		return tipoConv;
	}

	public void setTipoConv(String tipoConv) {
		this.tipoConv = tipoConv;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getPlanOrigen() {
		return planOrigen;
	}

	public void setPlanOrigen(String planOrigen) {
		this.planOrigen = planOrigen;
	}
	
}