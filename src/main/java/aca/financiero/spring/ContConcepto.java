package aca.financiero.spring;

public class ContConcepto {
	private String id;
	private String version;	
	private String descripcion;
	private String status;
	private String nombre;
	private String tags;
	
	// Constructor
	public ContConcepto(){		
		id				= "";
		version			= "";
		descripcion		= "";
		status			= "";
		nombre			= "";
		tags			= "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
}