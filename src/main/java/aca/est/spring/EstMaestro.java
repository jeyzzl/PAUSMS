// Bean del Catalogo Cargas
package  aca.est.spring;

public class EstMaestro{	
	private String codigoPersonal;
	private String importe;
	private String horas;
	private String tipo;
	
	public EstMaestro(){
		
		codigoPersonal		= "0";
		importe				= "0";
		horas				= "0";
		tipo 				= "M";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}