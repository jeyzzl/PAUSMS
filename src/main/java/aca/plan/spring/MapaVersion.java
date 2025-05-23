package  aca.plan.spring;

public class MapaVersion{
	
	private String versionId;
	private String versionNombre;	
	
	public MapaVersion(){
		versionId			= "0";
		versionNombre		= "-";		
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionNombre() {
		return versionNombre;
	}

	public void setVersionNombre(String versionNombre) {
		this.versionNombre = versionNombre;
	}
		
}