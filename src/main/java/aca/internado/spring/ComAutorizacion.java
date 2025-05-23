package aca.internado.spring;

public class ComAutorizacion {
	
	private String matricula;
	private String numComidas;
	private String tipoComida;
	private String fechaInicial;
	private String fechaFinal;
	private String usuario;
	private String cliente;
	private String paquete;
	private String cargaId;
	private String bloque;
	
	public ComAutorizacion(){
		matricula			= "";
		numComidas			= "";
		tipoComida			= "";
		fechaInicial		= "";
		fechaFinal			= "";
		usuario				= "";
		cliente				= "";
		paquete				= "";
		cargaId				= "";
		bloque				= "";
	}
	
	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumComidas() {
		return numComidas;
	}

	public void setNumComidas(String numComidas) {
		this.numComidas = numComidas;
	}

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}