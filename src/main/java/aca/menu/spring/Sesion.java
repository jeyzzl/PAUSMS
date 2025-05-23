// Bean del Catalogo Planes
package  aca.menu.spring;


public class Sesion{
	private String sesionId;
	private String codigoPersonal; 
	private String fInicio;	
	private String fFinal;
	private String ip;
	private String finalizo;
	
	
	public Sesion(){
		sesionId		= "";
		codigoPersonal	= "";
		fInicio			= "";		
		fFinal			= "";
		ip				= "";
		finalizo		= "";
	}
	
	public String getSesionId(){
		return sesionId;
	}
	
	public void setSesionId( String sesionId){
		this.sesionId = sesionId;
	}	
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
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
	
	public String getIp(){
		return ip;
	}
	
	public void setIp( String ip){
		this.ip = ip;
	}
	
	/**
	 * @return the finalizo
	 */
	public String getFinalizo() {
		return finalizo;
	}

	/**
	 * @param finalizo the finalizo to set
	 */
	public void setFinalizo(String finalizo) {
		this.finalizo = finalizo;
	}
}