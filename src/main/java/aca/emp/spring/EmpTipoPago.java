// Bean del Catalogo EmpTipoPago
package  aca.emp.spring;

public class EmpTipoPago{
	private String tipopagoId;
	private String tipopagoNombre;
	private String corto;
	
	public EmpTipoPago(){
		tipopagoId			= "0"; 
		tipopagoNombre		= "0";
		corto				= "-";	
	}
	
	public String getTipopagoId() {
		return tipopagoId;
	}
	
	public void setTipopagoId(String tipopagoId) {
		this.tipopagoId = tipopagoId;
	}

	public String getTipopagoNombre() {
		return tipopagoNombre;
	}

	public void setTipopagoNombre(String tipopagoNombre) {
		this.tipopagoNombre = tipopagoNombre;
	}

	public String getCorto() {
		return corto;
	}

	public void setCorto(String corto) {
		this.corto = corto;
	}
	
}