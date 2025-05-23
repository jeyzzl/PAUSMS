package  aca.diploma.spring;

public class DipAlumno{
	private String diplomaId;
	private String codigoPersonal;
	private String nombre;
	private byte[] imagenQr;
	
	public DipAlumno(){
		diplomaId		= "0";
		codigoPersonal	= "-";
		nombre			= "-";
		imagenQr		= null;
	}
	
	public String getDiplomaId() {
		return diplomaId;
	}
	public void setDiplomaId(String diplomaId) {
		this.diplomaId = diplomaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImagenQr() {
		return imagenQr;
	}
	public void setImagenQr(byte[] imagenQr) {
		this.imagenQr = imagenQr;
	}	
}