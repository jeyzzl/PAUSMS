// Clase para la tabla de Modulo
package aca.candado;
import java.sql.*;

public class CandTipo{	
	private String tipoId;
	private String nombreTipo;
	private String responsable;	
	private String lugar;
	private String telefono;	
	
	// Constructor
	public CandTipo(){		
		tipoId			= "";
		nombreTipo		= "";
		responsable		= "";
		lugar			= "";
		telefono		= "";
	}

	/**
	 * @return Returns the tipoId.
	 */
	public String getTipoId() {
		return tipoId;
	}
	/**
	 * @param tipoId The tipoId to set.
	 */
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
	
	/**
	 * @return Returns the nombreTipo.
	 */
	public String getNombreTipo() {
		return nombreTipo;
	}
	/**
	 * @param nombreTipo The nombreTipo to set.
	 */
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	/**
	 * @return Returns the responsable.
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable The responsable to set.
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
		
	/**
	 * @return Returns the lugar.
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar The lugar to set.
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	/**
	 * @return Returns the telefono.
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono The telefono to set.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}		
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		tipoId  	= rs.getString("TIPO_ID");
		nombreTipo	= rs.getString("NOMBRE_TIPO");
		responsable	= rs.getString("RESPONSABLE");		
		lugar		= rs.getString("LUGAR");
		telefono	= rs.getString("TELEFONO");
	}
	
	public void mapeaRegId(Connection conn, String tipoId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT "+
				"TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO "+				
				"FROM ENOC.CAND_TIPO WHERE TIPO_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,tipoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandTipo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}