package  aca.saum.spring;

public class SaumIngrediente{
	private String id;	
	private String version;
	private String cantidad;
	private String etapaId;
	private String materiaId;
	private String presentacion;
	private String unidadMedida;
	private String recetaId;
	
	public SaumIngrediente(){
		id 				= "";
		version			= "";
		cantidad		= "";
		etapaId			= "";
		materiaId		= "";
		presentacion	= "";
		unidadMedida	= "";
		recetaId		= "0";
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

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getEtapaId() {
		return etapaId;
	}

	public void setEtapaId(String etapaId) {
		this.etapaId = etapaId;
	}

	public String getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(String materiaId) {
		this.materiaId = materiaId;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getRecetaId() {
		return recetaId;
	}

	public void setRecetaId(String recetaId) {
		this.recetaId = recetaId;
	}
	
}