package aca.bec.spring;

public class BecCategoria {
	private String categoriaId;
	private String categoriaNombre;
	private String usuario;
	private String estado;
	private String pdf;
	
	public BecCategoria(){
		categoriaId			= "0";
		categoriaNombre		= "-";
		usuario    			= "0";
		estado 				= "A";
		pdf 				= "-";
	}
	
	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	public String getCategoriaNombre() {
		return categoriaNombre;
	}
	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
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