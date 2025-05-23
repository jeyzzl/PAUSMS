/**
 * 
 */
package aca.mentores.spring;

/**
 * @author Elifo
 *
 */
public class MentContacto {
	private String periodoId;
	private String mentorId;
	private String contactoId;
	private String codigoPersonal;
	private String motivoId;
	private String miAconsejado;
	private String fecha;
	private String fechaContacto;
	private String comentario;
	private String tipo;
	private String carreraId;
	private String motivos;
	
	public MentContacto(){
		periodoId		= "0";
		mentorId		= "0";
		contactoId		= "0";
		codigoPersonal	= "0";
		motivoId		= "0";
		miAconsejado	= "-";
		fecha			= aca.util.Fecha.getHoy();
		fechaContacto	= aca.util.Fecha.getHoy();
		comentario		= "-";
		tipo			= "0";
		carreraId		= "0";
		motivos			= "0";
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	public String getContactoId() {
		return contactoId;
	}

	public void setContactoId(String contactoId) {
		this.contactoId = contactoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(String motivoId) {
		this.motivoId = motivoId;
	}

	public String getMiAconsejado() {
		return miAconsejado;
	}

	public void setMiAconsejado(String miAconsejado) {
		this.miAconsejado = miAconsejado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaContacto() {
		return fechaContacto;
	}

	public void setFechaContacto(String fechaContacto) {
		this.fechaContacto = fechaContacto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	
	public String getMotivos() {
		return motivos;
	}

	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}	
}