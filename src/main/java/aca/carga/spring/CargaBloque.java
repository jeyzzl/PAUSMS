// Bean del Catalogo de Bloques
package  aca.carga.spring;

public class CargaBloque{
	private String cargaId;
	private String bloqueId;
	private String nombreBloque;
	private String fInicio;
	private String fFinal;	
	private String fCierre;
	private String inicioClases;
	
	public CargaBloque(){
		cargaId			= "";
		bloqueId		= "0";
		nombreBloque	= "";
		fInicio			= "";
		fFinal			= "";
		fCierre			= "";
		inicioClases 	= "";
	}
	
	public String getInicioClases() {
		return inicioClases;
	}
	public void setInicioClases(String inicioClases) {
		this.inicioClases = inicioClases;
	}

	public String getCargaId(){
		return cargaId;
	}	
	public void setCargaId( String cargaId){
		this.cargaId = cargaId;
	}	
	
	public String getBloqueId(){
		return bloqueId;
	}	
	public void setBloqueId( String bloqueId){
		this.bloqueId = bloqueId;
	}
	
	public String getNombreBloque(){
		return nombreBloque;
	}	
	public void setNombreBloque( String nombreBloque){
		this.nombreBloque = nombreBloque;
	}
	
	public String getFInicio(){
		return fInicio;
	}	
	public void setFInicio( String fInicio){
		this.fInicio = fInicio;
	}
	
	public String getFFinal(){
		return fFinal;
	}	
	public void setFFinal( String fFinal){
		this.fFinal = fFinal;
	}
	
	public String getFCierre(){
		return fCierre;
	}	
	public void setFCierre( String fCierre){
		this.fCierre = fCierre;
	}	
}