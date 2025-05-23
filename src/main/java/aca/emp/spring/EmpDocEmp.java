// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpDocEmp{
	private String codigoPersonal;
	private String documentoId;
	private String hoja;
	private byte[] imagen;
	private String usuario;
	private String fecha;
	
	public EmpDocEmp(){
		codigoPersonal 	= "";
		documentoId 	= "0";
		hoja 			= "0";
		imagen			= null;
		usuario			= "-";
		fecha			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getDocumentoId() {
		return documentoId;
	}


	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}


	public String getHoja() {
		return hoja;
	}


	public void setHoja(String hoja) {
		this.hoja = hoja;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}