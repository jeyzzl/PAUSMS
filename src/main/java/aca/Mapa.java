// Bean de Estado del alumno en el procesos de matrxcula( Por cada bloque).
package  aca;

public class Mapa{
	private String llave;
	private String valor;
	
	public Mapa(){
		llave			= "";
		valor			= "";
	}
	
	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}