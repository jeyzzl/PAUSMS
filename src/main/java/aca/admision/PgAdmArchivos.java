package aca.admision;


import java.sql.ResultSet;
import java.sql.SQLException;


public class PgAdmArchivos {
	private String folio;
	private String documentoId;	
	private long archivo;
	private String nombre;
	
	public PgAdmArchivos(){
		folio		= "";
		documentoId	= "";		
		archivo		= 0;
		nombre 		= "";
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
	 * @return the archivo
	 */
	public long getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(long archivo) {
		this.archivo = archivo;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");		
		archivo			= rs.getInt("ARCHIVO");
		nombre		 	= rs.getString("NOMBRE");
	}
	
}