package aca.admision.spring;

public class AdmEstudio {
	private String folio;
	private String Id;
	private String titulo;
	private String institucion;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String completo;
	private String inicio;
	private String fin;
	private String dependencia;
	private String convalida;
	private String estudios;
	private String otraMateria;
		
	public AdmEstudio(){
		folio 			= "";
		Id		 		= "";
		titulo	 		= "";
		institucion		= "";
		paisId 			= "";
		estadoId 		= "";
		ciudadId 		= "";
		completo		= "";
		inicio 			= "";
		fin 			= "";
		dependencia 	= "";
		convalida 		= "";
		estudios 		= "";
		otraMateria 	= "";
		
	}
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
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
	public String getPaisId() {
		return paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}
	public String getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}
	public String getCiudadId() {
		return ciudadId;
	}
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
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

	public String getEstudios() {
		return estudios;
	}
	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getOtraMateria() {
		return otraMateria;
	}
	public void setOtraMateria(String otraMateria) {
		this.otraMateria = otraMateria;
	}
}