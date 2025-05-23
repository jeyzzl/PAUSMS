package  aca.alumno.spring;

public class AlumEstudio{
	
	private String codigoPersonal;
	private String id;
	private String titulo;
	private String institucion;
	private String completo;
	private String inicio;
	private String fin;
	private String dependencia;
	private String convalida;
	
	public AlumEstudio() {
		codigoPersonal 	= "";
		id 				= "";
		titulo			= "";
		institucion 	= "";
		completo 		= "";
		inicio 			= "";
		fin 			= "";
		dependencia 	= "";
		convalida 		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getConvalida() {
		return convalida;
	}

	public void setConvalida(String convalida) {
		this.convalida = convalida;
	}
}