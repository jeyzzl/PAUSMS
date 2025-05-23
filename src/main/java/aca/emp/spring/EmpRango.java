// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpRango{
	private String rangoId;
	private String rangoNombre;
	private String minimo;
	private String maximo;
	private String precioMin;
	private String precioMax;
	
	public EmpRango(){
		rangoId 	= "0";
		rangoNombre = "-";
		minimo 		= "0";
		maximo 		= "0";
		precioMin	= "0";
		precioMax	= "0";
	}

	public String getRangoId() {
		return rangoId;
	}

	public void setRangoId(String rangoId) {
		this.rangoId = rangoId;
	}

	public String getRangoNombre() {
		return rangoNombre;
	}

	public void setRangoNombre(String rangoNombre) {
		this.rangoNombre = rangoNombre;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getPrecioMin() {
		return precioMin;
	}

	public void setPrecioMin(String precioMin) {
		this.precioMin = precioMin;
	}

	public String getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(String precioMax) {
		this.precioMax = precioMax;
	}
	
}