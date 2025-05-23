package aca.apFisica.spring;

public class ApFisicaGrupo{	
	private String grupoId;
	private String nombreGrupo;
	private String lugar;
	private String instructor;
	private String cupo;
	private String fInicio;
	private String fFinal;
	private String dia1;
	private String cargas;
	private String clave;
	private String descripcion;
	private String hora;
	private String fCierre;
	private String acceso;
	private String liga;
	private String sexo;
	
	// Constructor
	public ApFisicaGrupo(){
		grupoId			= "0";
		nombreGrupo		= "-";
		lugar			= "-";
		instructor		= "-";
		cupo			= "0";
		fInicio			= aca.util.Fecha.getHoy();
		fFinal			= aca.util.Fecha.getHoy();
		dia1			= "0";
		cargas			= "";
		clave			= "-";
		descripcion		= "-";
		hora			= "0";
		fCierre			= aca.util.Fecha.getHoy();
		acceso			= "T";
		liga 			= "-";
		sexo			= "T";
	}
	
	public String getGrupoId() {
		return grupoId;
	}
	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getDia1() {
		return dia1;
	}

	public void setDia1(String dia1) {
		this.dia1 = dia1;
	}
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}	

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}
	
	public String getfCierre() {
		return fCierre;
	}

	public void setfCierre(String fCierre) {
		this.fCierre = fCierre;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public String getLiga() {
		return liga;
	}
	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}	
	
}