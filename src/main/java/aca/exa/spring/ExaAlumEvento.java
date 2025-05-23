package  aca.exa.spring;

public class ExaAlumEvento{
	private String alumEventoId;	
	private String matricula;
	private String idEvento;
	private String fechaActualizacion;	
	private String eliminado;
	private String idEventoAsistido;
	
	public ExaAlumEvento(){
		alumEventoId 		= "";
		matricula			= "";
		idEvento 			= "";
		fechaActualizacion	= "";
		eliminado	 		= "";
		idEventoAsistido	= "";
	}
	
	public String getAlumEventoId() {
		return alumEventoId;
	}
	public void setAlumEventoId(String alumEventoId) {
		this.alumEventoId = alumEventoId;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getEliminado() {
		return eliminado;
	}
	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEventoAsistido() {
		return idEventoAsistido;
	}
	public void setIdEventoAsistido(String idEventoAsistido) {
		this.idEventoAsistido = idEventoAsistido;
	}
}