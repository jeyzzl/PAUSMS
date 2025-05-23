// Bean del Catalogo de grade point
package  aca.catalogo.spring;

public class CatGradePoint{
	private	String gpId;
	private String gpNombre;
	private String inicio;
	private String fin;
	private String puntos;
	private String titulo;
	
	public CatGradePoint(){	
		gpId		= "";
		gpNombre	= "";
		inicio		= "";
		fin			= "";
		puntos		= "";
		titulo		= "";		
	}
	
	public String getGpId() {
		return gpId;
	}

	public void setGpId(String gpId) {
		this.gpId = gpId;
	}

	public String getGpNombre() {
		return gpNombre;
	}

	public void setGpNombre(String gpNombre) {
		this.gpNombre = gpNombre;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}