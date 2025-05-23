// Bean de ActivoImagen
package  aca.pg.archivo.spring;

public class PosArchResidencia{
	private String codigoPersonal;
	private String folio;
	private byte[] imagen;	
		
	public PosArchResidencia(){
		codigoPersonal	= "";
		folio			= "O";
		imagen			= null;		
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
}