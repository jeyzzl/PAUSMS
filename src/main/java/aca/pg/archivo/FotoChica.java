// Bean de ActivoImagen
package  aca.pg.archivo;

public class FotoChica{
	private String codigoPersonal;		
	private String fecha;
	private byte[] foto;	
		
	public FotoChica(){
		codigoPersonal	= "";
		fecha 			= "";
		foto			= null;	
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
}