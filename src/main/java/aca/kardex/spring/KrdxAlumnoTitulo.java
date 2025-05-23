//Bean de la tablas KRDX_CURSO_ACT 
package  aca.kardex.spring;

public class KrdxAlumnoTitulo{
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String carreraId;
	private String presidente;
	private String secretario;
	private String miembro;
	private String comentario;
	private String nota;
	private String usuario;
	private String estado;
	
	public KrdxAlumnoTitulo(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		carreraId		= "";
		presidente		= "";
		secretario		= "";
		miembro 		= "";
		comentario		= "";
		nota			= "";
		usuario			= "";
		estado			= "";		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	public String getMiembro() {
		return miembro;
	}

	public void setMiembro(String miembro) {
		this.miembro = miembro;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}