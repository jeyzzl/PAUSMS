// Bean del Catalogo de CarFreras
package  aca.catalogo.spring;

public class CatCarrera{	
	private	String carreraId;	
	private String nivelId;
	private String titulo;
	private String nombreCarrera;
	private String nombreCorto;
	private String ccostoId;
	private String codigoPersonal;
	private String facultadId;
	private String glsetno;
	private String costcentcd;
	private String dscntpct;
	
	public CatCarrera(){		
		carreraId		= "0";
		nivelId			= "0";
		titulo			= "";
		nombreCarrera	= "";
		nombreCorto		= "";
		ccostoId		= "";
		codigoPersonal	= "";
		glsetno			= "";
		costcentcd 		= "";
		dscntpct 		= "";		
	}
	
	public String getCarreraId(){
		return carreraId;
	}
	public void setCarreraId( String carreraId){
		this.carreraId = carreraId;
	}
	
	public String getNivelId(){
		return nivelId;
	}
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}
	
	public String getTitulo(){
		return titulo;
	}
	public void setTitulo( String titulo){
		this.titulo = titulo;
	}
	
	public String getNombreCarrera(){
		return nombreCarrera;
	}
	public void setNombreCarrera( String nombreCarrera){
		this.nombreCarrera = nombreCarrera;
	}
		
	public String getNombreCorto(){
		return nombreCorto;
	}
	public void setNombreCorto( String nombreCorto){
		this.nombreCorto = nombreCorto;
	}
	
	public String getCcostoId(){
		return ccostoId;
	}
	public void setCcostoId( String ccostoId){
		this.ccostoId = ccostoId;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}

	public String getFacultadId() {
		return facultadId;
	}
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getGlsetno() {
		return glsetno;
	}
	public void setGlsetno(String glsetno) {
		this.glsetno = glsetno;
	}

	public String getCostcentcd() {
		return costcentcd;
	}
	public void setCostcentcd(String costcentcd) {
		this.costcentcd = costcentcd;
	}

	public String getDscntpct() {
		return dscntpct;
	}
	public void setDscntpct(String dscntpct) {
		this.dscntpct = dscntpct;
	}
	
}