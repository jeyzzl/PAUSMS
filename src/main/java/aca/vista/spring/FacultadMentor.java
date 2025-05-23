//Clase para la vista MOD_OPCION
package aca.vista.spring;

public class FacultadMentor{
	private String idMentor;
	private String facultadId;
	private String nombreFacultad;	

	public FacultadMentor(){
		idMentor 		="";	
		facultadId 		="";
		nombreFacultad	="";		
	}
	
	

	public String getIdMentor() {
		return idMentor;
	}



	public void setIdMentor(String idMentor) {
		this.idMentor = idMentor;
	}



	public String getFacultadId() {
		return facultadId;
	}



	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}



	public String getNombreFacultad() {
		return nombreFacultad;
	}



	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}
	
}