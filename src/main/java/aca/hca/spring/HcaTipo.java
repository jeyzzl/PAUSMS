/**
 * 
 */
package aca.hca.spring;
/**
 * @author etorres 
 */
public class HcaTipo {
	private String tipoId;
	private String tipoNombre;
	private String orden;
	
	public HcaTipo(){
		tipoId		= "";
		tipoNombre	= "";
		orden		= "";
	}

	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId){
		this.tipoId = tipoId;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}
	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}	
}