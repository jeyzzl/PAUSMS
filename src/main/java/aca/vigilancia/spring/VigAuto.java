package aca.vigilancia.spring;

public class VigAuto{
	 
	private String autoId;
	private String placas;
	private String engomado;
	private String usuario;	
	private String comentario;
	private String color; 
	private String modelo; 
	private String marca;
	private String poliza; 
	private String fecha;
	private String estado;
	
	public VigAuto(){
		autoId				= "0";
		placas				= "";
		engomado			= ""; 		
		usuario				= "0";		
		comentario			= "-";
		color				= "";
		modelo				= "";
		marca				= "";
		poliza				= ""; 	
		fecha				= aca.util.Fecha.getHoyReversa();
		estado 				= "A";
	} 	

	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getPlacas() {
		return placas;
	}
	public void setPlacas(String placas) {
		this.placas = placas;
	}

	public String getEngomado() {
		return engomado;
	}
	public void setEngomado(String engomado) {
		this.engomado = engomado;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
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
}