package aca.admision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PgAdmDocAlum {
	private String folio;
	private String documentoId;
	private String hoja;
	private long imagen;
	
	public PgAdmDocAlum(){
		folio		= "";
		documentoId	= "";
		hoja		= "";
		imagen		= 0;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the documentoId
	 */
	public String getDocumentoId() {
		return documentoId;
	}

	/**
	 * @param documentoId the documentoId to set
	 */
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	/**
	 * @return the imagen
	 */
	public long getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * @return the hoja
	 */
	public String getHoja() {
		return hoja;
	}

	/**
	 * @param hoja the hoja to set
	 */
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");
		hoja	 		= rs.getString("HOJA");
		imagen 			= rs.getLong("IMAGEN");
	}
		
}