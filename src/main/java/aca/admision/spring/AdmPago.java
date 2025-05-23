package aca.admision.spring;

public class AdmPago {
	private String folio;
	private String cantidad;
	private String fecha;
	private String comentario;
	private String metodo;
	private String recibo;
	private byte[] archivo;
	private String nombre;
	
	public AdmPago() {
		folio		= "0";
		cantidad	= "-";
		fecha 		= "-";
		comentario 	= "-";
		metodo 		= "-";
		recibo 		= "-";
		archivo 	= null;
		nombre  	= "";
	}	
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getRecibo() {
		return recibo;
	}
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}