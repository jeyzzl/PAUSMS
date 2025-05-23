// Bean del Catalogo de Estados
package  aca.catalogo.spring;

public class CatTipoCal{
	private String tipoCalId;
	private String nombreTipoCal;
	private String nombreCorto;
	
	public CatTipoCal(){
		tipoCalId 			= "";
		nombreTipoCal 		= "";
		nombreCorto			= "";
	}
	
	public CatTipoCal(String tipoCalId, String nombreTipo){
		this.tipoCalId 		= tipoCalId;
		nombreTipoCal 		= nombreTipo;		
	}
	
	public CatTipoCal(String tipoCalId, String nombreTipo, String corto){
		this.tipoCalId 		= tipoCalId;
		nombreTipoCal 		= nombreTipo;		
		nombreCorto 		= corto;
	}
	
	public String getTipoCalId(){
		return tipoCalId;
	}
	
	public void setTipoCalId( String tipoCalId){
		this.tipoCalId = tipoCalId;
	}
	
	public String getNombreTipoCal(){
		return nombreTipoCal;
	}
	
	public void setNombreTipoCal( String nombreTipoCal){
		this.nombreTipoCal = nombreTipoCal;
	}
	
	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
}