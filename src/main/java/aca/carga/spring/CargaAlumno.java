//Bean del Catalogo Cargas
package  aca.carga.spring;

public class CargaAlumno{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String planId;
	private String fecha;
	private String estado;
	private String grupo;
	private String confirmar;
	private String pago;
	private String modo;
	private String beca;
	private String calculo;	
	private String comentario;	
	private String ingreso;
	private String mat;
	
	public CargaAlumno(){
		codigoPersonal	= "0";
		cargaId			= "0";
		bloqueId		= "0";
		planId			= "0";
		fecha			= "";
		estado			= "1";
		grupo			= "XX";
		confirmar		= "N";
		pago 			= "N";
		modo 			= "P";
		beca 			= "N";
		calculo			= "N";		
		comentario		= "";
		ingreso			= "X";
		mat				= "N";		
	}
	
	public String getCalculo() {
		return calculo;
	}
	public void setCalculo(String calculo) {
		this.calculo = calculo;
	}

	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
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

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBloqueId() {
		return bloqueId;
	}
	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getConfirmar() {
		return confirmar;
	}
	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getPago() {
		return pago;
	}
	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getBeca() {
		return beca;
	}
	public void setBeca(String beca) {
		this.beca = beca;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getIngreso() {
		return ingreso;
	}
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	public String getMat() {
		return mat;
	}

	public void setMat(String mat) {
		this.mat = mat;
	}
	
}
