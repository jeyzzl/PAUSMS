 // BEANS DE LA TABLA COND_ALUMNO
 
package aca.disciplina.spring;

public class CondAlumno {
	private String matricula;	
	private String periodoId;
	private String folio;
	private String idReporte;	
	private String idLugar;
	private String idJuez;
	private String empleado;
	private String fecha;
	private String cantidad;
	private String comentario;
	private String planId;
	
	public CondAlumno(){
		matricula 	= "0";
		periodoId  	= "0";
		folio	  	= "0";
		idReporte 	= "0";
		idLugar  	= "0";
		idJuez	  	= "0";
		empleado 	= "9800069";
		fecha  		= "";
		cantidad	= "0";
		comentario	= "-";
		planId 		= "0";
	}
	
	public String getMatricula(){
		return matricula;
	}	
	public void setMatricula( String matricula){
		this.matricula = matricula;
	}
	
	public String getPeriodoId(){
		return periodoId;
	}	
	public void setPeriodoId( String periodoId){
		this.periodoId = periodoId;
	}
	
	public String getFolio(){
		return folio;
	}	
	public void setFolio( String folio){
		this.folio = folio;
	}
	public String getIdReporte(){
		return idReporte;
	}
	
	public void setIdReporte( String idReporte){
		this.idReporte = idReporte;
	}	
	public String getIdLugar(){
		return idLugar;
	}
	
	public void setIdLugar( String idLugar){
		this.idLugar = idLugar;
	}
	
	public String getIdJuez(){
		return idJuez;
	}	
	public void setIdJuez( String idJuez){
		this.idJuez = idJuez;
	}
	
	public String getEmpleado(){
		return empleado;
	}	
	public void setEmpleado( String empleado){
		this.empleado = empleado;
	}
	
	public String getFecha(){
		return fecha;
	}	
	public void setFecha( String fecha){
		this.fecha = fecha;
	}
	
	public String getCantidad(){
		return cantidad;
	}	
	public void setCantidad( String cantidad){
		this.cantidad = cantidad;
	}
	
	public String getComentario(){
		return comentario;
	}	
	public void setComentario( String comentario){
		this.comentario = comentario;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}	
}