// Bean de Cursos Importados
package  aca.kardex.spring;


public class KrdxMaximo{
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluadas;
	private String puntos;
	private String maximo;
	private String carreraId;
	
	public KrdxMaximo(){
		codigoPersonal		= "";
		cursoCargaId		= "";
		evaluadas			= "";
		puntos				= "";
		maximo				= "";
		carreraId			= "";

	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEvaluadas() {
		return evaluadas;
	}

	public void setEvaluadas(String evaluadas) {
		this.evaluadas = evaluadas;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	
}