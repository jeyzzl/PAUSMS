
// Bean del Catalogo Planes
package  aca.alumno.spring;

public class AlumPlan{
	private String codigoPersonal;
	private String planId;	
	private String fInicio;
	private String estado;
	private String escuelaId;
	private String avanceId;
	private String permiso;
	private String evento;
	private String fGraduacion;
	private String fEgreso;
	private String grado;
	private String ciclo;
	private String principal;
	private String escala;
	private String primerMatricula;	
	private String actualizado;
	private String cicloSep;
	private String promedio;
	private String creditos;
	private String finalizado;
	private String mayor;
	private String menor;

	public AlumPlan(){
		codigoPersonal	= "0";
		planId			= "0";		
		fInicio			= aca.util.Fecha.getHoy();
		estado			= "1";
		escuelaId		= "1";
		avanceId		= "0";
		permiso			= "0";
		evento			= "0";
		fGraduacion 	= "";
		fEgreso			= "";
		grado			= "0";
		ciclo			= "0";
		principal		= "N";
		escala			= "10";
		primerMatricula = "";
		actualizado		= "N";
		cicloSep		= "0";
		promedio		= "0";
		creditos 		= "0";
		finalizado 		= "N"; 
		mayor 			= "0";
		menor 			= "0";
	}
	
	public String getEscala() {
		return escala;
	}
		
	public void setEscala(String escala) {
		this.escala = escala;
	}

	public String getGrado() {
		return grado;
	}
	
	public void setGrado(String grado) {
		this.grado = grado;
	}
	
	public String getCiclo() {
		return ciclo;
	}
	
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	
	public String getFEgreso() {
		return fEgreso;
	}

	public void setFEgreso(String egreso) {
		fEgreso = egreso;
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getEscuelaId() {
		return escuelaId;
	}
	
	public void setEscuelaId(String escuelaId) {
		this.escuelaId = escuelaId;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	
	public String getFGraduacion() {
		return fGraduacion;
	}	
	
	public void setFGraduacion(String fGraduacion) {
		this.fGraduacion = fGraduacion;
	}	
	
	public String getFInicio() {
		return fInicio;
	}
	
	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}
	
	public String getPlanId() {
		return planId;
	}
	
	public void setPlanId(String planId) {
		this.planId = planId;
	}	
	
	public String getAvanceId() {
		return avanceId;
	}
	
	public void setAvanceId(String avanceId) {
		this.avanceId = avanceId;
	}
	
	public String getEvento() {
		return evento;
	}
	
	public void setEvento(String evento) {
		this.evento = evento;
	}
	
	public String getPermiso() {
		return permiso;
	}
	
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}	
	
	public String getPrincipal() {
		return principal;
	}
	
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getPrimerMatricula() {
		return primerMatricula;
	}
	
	public void setPrimerMatricula(String primerMatricula) {
		this.primerMatricula = primerMatricula;
	}	
	
	public String getActualizado() {
		return actualizado;
	}
	
	public void setActualizado(String actualizado) {
		this.actualizado = actualizado;
	}
	
	public String getCicloSep() {
		return cicloSep;
	}
	
	public void setCicloSep(String cicloSep) {
		this.cicloSep = cicloSep;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public String getMayor() {
		return mayor;
	}
	public void setMayor(String mayor) {
		this.mayor = mayor;
	}

	public String getMenor() {
		return menor;
	}
	public void setMenor(String menor) {
		this.menor = menor;
	}	

	
}