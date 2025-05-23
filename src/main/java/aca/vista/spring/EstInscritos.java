// CLASE PARA LA VISTA EST_INSCRITOS
package  aca.vista.spring;

public class EstInscritos{
	private String codigoPersonal;
	private String nombre;
	private String edad;
	private String sexo;
	private String residenciaId;
	private String nombreReligion;	
	private String tipo;
	private String cargaId;
	private String modalidad;	
	private String planId;	
		
	public EstInscritos(){
		codigoPersonal	= "";
		nombre			= "";
		edad			= "";
		sexo			= "";
		residenciaId	= "";
		nombreReligion	= "";
		tipo			= "";
		cargaId			= "";	
		modalidad		= "";
		planId			= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public String getNombre(){
		return nombre;
	}	
	public String getEdad(){
		return edad;
	}
	
	public String getSexo(){
		return sexo;
	}	
	
	public String getResidenciaId(){
		return residenciaId;
	}	
	
	public String getNombreReligion(){
		return nombreReligion;
	}
	
	public String getTipo(){
		return tipo;
	}	
	
	public String getCargaId(){
		return cargaId;
	}	
	
	public String getModalidad(){
		return modalidad;
	}
	
	public String getPlanId(){
		return planId;
	}
		
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}

	public void setNombreReligion(String nombreReligion) {
		this.nombreReligion = nombreReligion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

}