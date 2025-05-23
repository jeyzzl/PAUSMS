package aca.rotaciones.spring;

public class RotHospitalEspecialidad {

	private String hospitalId;
	private String especialidadId;
	private String contactoPrincipal;
	private String puestoPrincipal;
	private String contactoSecundario;
	private String puestoSecundario;
	private String estado;
	
	public RotHospitalEspecialidad(){		
		hospitalId			= "";
		especialidadId		= "";
		contactoPrincipal	= "";
		puestoPrincipal 	= "";
		contactoSecundario	= "";
		puestoSecundario	= "";
		estado				= "A";
	}
	

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getContactoPrincipal() {
		return contactoPrincipal;
	}

	public void setContactoPrincipal(String contactoPrincipal) {
		this.contactoPrincipal = contactoPrincipal;
	}

	public String getPuestoPrincipal() {
		return puestoPrincipal;
	}

	public void setPuestoPrincipal(String puestoPrincipal) {
		this.puestoPrincipal = puestoPrincipal;
	}

	public String getContactoSecundario() {
		return contactoSecundario;
	}

	public void setContactoSecundario(String contactoSecundario) {
		this.contactoSecundario = contactoSecundario;
	}

	public String getPuestoSecundario() {
		return puestoSecundario;
	}

	public void setPuestoSecundario(String puestoSecundario) {
		this.puestoSecundario = puestoSecundario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
