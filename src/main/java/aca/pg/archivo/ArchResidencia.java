// Bean de ActivoImagen
package  aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchResidencia{
	private String codigoPersonal;
	private String folio;
	private byte[] imagen;	
		
	public ArchResidencia(){
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

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		folio				= rs.getString("FOLIO");
		imagen				= rs.getBytes("IMAGEN");		
	}
	
	public void mapeaRegCorto(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		folio				= rs.getString("FOLIO");		
	}	
}