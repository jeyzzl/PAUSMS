package aca.financiero.spring;

public class FesCcMateria {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String cursoCargaId;
	private String cursoId;
	private String costoCredito;
	private String costoCurso;
	
	public FesCcMateria(){
		matricula		= "";
		cargaId			= "";
		bloque			= "";      
		cursoCargaId	= "";
		cursoId			= "";
		costoCredito	= "";
		costoCurso		= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCostoCredito() {
		return costoCredito;
	}

	public void setCostoCredito(String costoCredito) {
		this.costoCredito = costoCredito;
	}

	public String getCostoCurso() {
		return costoCurso;
	}

	public void setCostoCurso(String costoCurso) {
		this.costoCurso = costoCurso;
	}
}