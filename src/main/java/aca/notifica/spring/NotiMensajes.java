package aca.notifica.spring;

public class NotiMensajes {
	private String id;
	
	private String codigoPersonal;
	
	private String tipo;
	
	private String mensaje;	
	
	private String url;
	
	private String silenciado;
	
	private String visto;
	
	private String fecha;
	
	private String sms;
	
	
	public NotiMensajes(){
		id				= "0";
		codigoPersonal	= "0";
		tipo			= "0";
		mensaje			= "";
		url				= "";
		silenciado		= "N";
		visto			= "N";
		fecha			= aca.util.Fecha.getHoy();		
		sms				= "N";	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo - el tipo de mensajes
	 * 
	 * Solo debe haber un tipo de mensaje por persona y este debe ser actualizado si llega una segunda notificación del mismo tipo.
	 * 
	 * El Tipo 1 se da de alta cuando se manda generar un cálculo de cobro
	 * 	y tiene que enviar a la pagina del paso 2 de inscripción justo en la
	 * 	parte donde se revisan las materias.
	 * 	Todo esto solo se da de alta en la notificación si ya existe el 
	 * 	cálculo de cobro.
	 * 	El campo de datos trae cargaId+","+bloqueId+","+periodoId
	 * 	(El número de Tipo siempre debe concordar entre NOTI_MENSAJES y NOTI_PROCESO_PENDIENTE).
	 * 
	 * El Tipo 2 se da de alta cuando se solicita una inscripción a un plan de parte del alumno
	 * */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSilenciado() {
		return silenciado;
	}

	public void setSilenciado(String silenciado) {
		this.silenciado = silenciado;
	}

	public String getVisto() {
		return visto;
	}

	public void setVisto(String visto) {
		this.visto = visto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}
	
}