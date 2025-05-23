/*
 * Created on 13/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

public class SsocRequisito {
	
	private String requisitoId;
    private String requisitoNombre;
    private String orden;   
    
    public SsocRequisito() {    	
    	requisitoId 		= "0";
    	requisitoNombre 	= "-";
    	orden				= "0";	 
    }

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public String getRequisitoNombre() {
		return requisitoNombre;
	}

	public void setRequisitoNombre(String requisitoNombre) {
		this.requisitoNombre = requisitoNombre;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
    
}