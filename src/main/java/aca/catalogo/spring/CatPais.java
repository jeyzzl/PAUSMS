// Bean del Catalogo de Paises
package  aca.catalogo.spring;

public class CatPais{
	private String paisId;	
	private String nombrePais;
	private String nacionalidad;
	private String interamerica;
	private String divisionId;
	private String semaforo;
	
	public CatPais(){
		paisId 			= "0";
		nombrePais 		= "-";
		nacionalidad	= "-";
		interamerica	= "N";
		divisionId 		= "0";
		semaforo		= "N";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getNombrePais(){
		return nombrePais;
	}
	
	public void setNombrePais( String nombrePais){
		this.nombrePais = nombrePais;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public void setNacionalidad( String nacionalidad){
		this.nacionalidad = nacionalidad;
	}

	public String getInteramerica() {
		return interamerica;
	}

	public void setInteramerica(String interamerica) {
		this.interamerica = interamerica;
	}	

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(String semaforo) {
		this.semaforo = semaforo;
	}
	
}