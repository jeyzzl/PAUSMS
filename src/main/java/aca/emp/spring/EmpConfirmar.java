// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpConfirmar{
	private String codigoPersonal;
	private String folio;
	private String usuario;
	private String fecha;
	
	public EmpConfirmar(){
		codigoPersonal		= "0";
		folio				= "0";
		usuario				= "0";
		fecha				= null;		
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}