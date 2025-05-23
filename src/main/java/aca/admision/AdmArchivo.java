package aca.admision;


import java.sql.ResultSet;
import java.sql.SQLException;


public class AdmArchivo {
	private String folio;
	private String documentoId;	
	private long archivo;
	private String nombre;
	private String fecha;
	
	public AdmArchivo(){
		folio		= "";
		documentoId	= "";		
		archivo		= 0;
		nombre 		= "";
		fecha 		= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	public long getArchivo() {
		return archivo;
	}

	public void setArchivo(long archivo) {
		this.archivo = archivo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");		
		archivo			= rs.getInt("ARCHIVO");
		nombre		 	= rs.getString("NOMBRE");
		fecha		 	= rs.getString("FECHA");
	}
	
}