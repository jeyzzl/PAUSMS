package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaUnidadInstrumento {
	private String cursoCargaId;
	private String instrumentoId;	
	private String instrumentoNombre;
	
	public CargaUnidadInstrumento(){
		cursoCargaId      = "";
		instrumentoId	  = "";
		instrumentoNombre = "";
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}
	
	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the instrumentoId
	 */
	public String getInstrumentoId() {
		return instrumentoId;
	}

	/**
	 * @param instrumentoId the instrumentoId to set
	 */
	public void setInstrumentoId(String instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	/**
	 * @return the instrumentoNombre
	 */
	public String getInstrumentoNombre() {
		return instrumentoNombre;
	}

	/**
	 * @param instrumentoNombre the instrumentoNombre to set
	 */
	public void setInstrumentoNombre(String instrumentoNombre) {
		this.instrumentoNombre = instrumentoNombre;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		instrumentoId 		= rs.getString("INSTRUMENTO_ID");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		instrumentoNombre   = rs.getString("INSTRUMENTO_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String instrumentoId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, INSTRUMENTO_ID, INSTRUMENTO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE CURSO_CARGA_ID = ? AND INSTRUMENTO_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,instrumentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadInstrumento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}