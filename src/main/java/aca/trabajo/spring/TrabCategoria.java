package aca.trabajo.spring;

public class TrabCategoria {
	
	private String categoriaId;
	private String nombreCategoria;
	private String estado;
	private String deptId;
	
	public TrabCategoria() {
		setCategoriaId("0");
		setNombreCategoria("-");
		setEstado("A");
		setDeptId("0");
	}
	public String getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}
