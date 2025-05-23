package  aca.exa.spring;

public class ExaFamilia{
	private String familiaId;	
	private String matricula;
	private String nombre;
	private String relacion;	
	private String fechaNac;
	private String fechaAct;
	private String eliminado;
	private String correo;	
	private String fechaAniv;
	private String idFamilia;
	
	public ExaFamilia(){
		familiaId 			= "0";
		matricula			= "-";
		nombre 				= "-";
		relacion			= "-";
		fechaNac	 		= "-";
		fechaAct			= "-";
		eliminado			= "0";
		correo				= "-";
		fechaAniv	 		= "-";
		idFamilia			= "-";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFechaAniv() {
		return fechaAniv;
	}

	public void setFechaAniv(String fechaAniv) {
		this.fechaAniv = fechaAniv;
	}

	public String getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(String idFamilia) {
		this.idFamilia = idFamilia;
	}
	
}