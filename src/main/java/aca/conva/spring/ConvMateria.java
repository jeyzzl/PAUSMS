package aca.conva.spring;

public class ConvMateria{
	
	private String convalidacionId;
	private String cursoId;
	private String fecha;
	private String tipo;
	private String estado;
	private String materia_O;
	private String creditos_O;
	private String nota_O;
	private String fNota;
	private String folio;
	
	public ConvMateria(){
		convalidacionId 	= "";
		cursoId				= "";
		fecha				= "";
		tipo				= "";
		estado				= "";
		materia_O			= "";
		creditos_O			= "";
		nota_O				= "";
		fNota				= "";
		folio				= "";
	}
	
	public String getfNota() {
		return fNota;
	}

	public void setfNota(String fNota) {
		this.fNota = fNota;
	}
	
	public String getConvalidacionId() {
		return convalidacionId;
	}
	
	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
	}
	
	public String getCursoId() {
		return cursoId;
	}
	
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getCreditos_O() {
		return creditos_O;
	}
	
	public void setCreditos_O(String creditos_O) {
		this.creditos_O = creditos_O;
	}

	public String getMateria_O() {
		return materia_O;
	}

	public void setMateria_O(String materia_O) {
		this.materia_O = materia_O;
	}

	public String getNota_O() {
		return nota_O;
	}

	public void setNota_O(String nota_O) {
		this.nota_O = nota_O;
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}