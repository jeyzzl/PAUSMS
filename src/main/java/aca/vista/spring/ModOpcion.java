//Clase para la vista MOD_OPCION
package aca.vista.spring;

public class ModOpcion{
	private String moduloId;
	private String opcionId;
	private String nombreModulo;
	private String nombreOpcion;
	private String urlModulo;
	private String urlOpcion;
	private String usuarios;

	public ModOpcion(){
		moduloId			="";	
		opcionId			="";
		nombreModulo		="";
		nombreOpcion		="";
		urlModulo			="";
		urlOpcion			="";
		usuarios			="";
	}

	public String getModuloId() {
		return moduloId;
	}

	public void setModuloId(String moduloId) {
		this.moduloId = moduloId;
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	public String getNombreModulo() {
		return nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public String getNombreOpcion() {
		return nombreOpcion;
	}

	public void setNombreOpcion(String nombreOpcion) {
		this.nombreOpcion = nombreOpcion;
	}

	public String getUrlModulo() {
		return urlModulo;
	}

	public void setUrlModulo(String urlModulo) {
		this.urlModulo = urlModulo;
	}

	public String getUrlOpcion() {
		return urlOpcion;
	}

	public void setUrlOpcion(String urlOpcion) {
		this.urlOpcion = urlOpcion;
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}

}