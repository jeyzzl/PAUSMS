/**
 * 
 */
package aca.hca.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo, Mushu a mvc
 *
 */
public class HcaActividad {
	private String tipoId;
	private String actividadId;
	private String actividadNombre;
	private String valor;
	private String nivelId;
	private String modificable;
	
	public HcaActividad(){
		tipoId			= "0";
		actividadId		= "0";
		actividadNombre	= "-";
		valor			= "0";
		nivelId			= "0";
		modificable		= "N";
	}

	/**
	 * @return the actividadId
	 */
	public String getActividadId() {
		return actividadId;
	}

	/**
	 * @param actividadId the actividadId to set
	 */
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	/**
	 * @return the actividadNombre
	 */
	public String getActividadNombre() {
		return actividadNombre;
	}

	/**
	 * @param actividadNombre the actividadNombre to set
	 */
	public void setActividadNombre(String actividadNombre) {
		this.actividadNombre = actividadNombre;
	}

	/**
	 * @return the nivelId
	 */
	public String getNivelId() {
		return nivelId;
	}

	/**
	 * @param nivelId the nivelId to set
	 */
	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	/**
	 * @return the tipoId
	 */
	public String getTipoId() {
		return tipoId;
	}

	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	/**
	 * @return the esModificable
	 */
	public String getModificable() {
		return modificable;
	}

	/**
	 * @param esModificable the esModificable to set
	 */
	public void setModificable(String modificable) {
		this.modificable = modificable;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		tipoId			= rs.getString("TIPO_ID");
		actividadId		= rs.getString("ACTIVIDAD_ID");
		actividadNombre	= rs.getString("ACTIVIDAD_NOMBRE");
		valor			= rs.getString("VALOR");
		nivelId			= rs.getString("NIVEL_ID");
		modificable		= rs.getString("MODIFICABLE");
	}
	
	public void mapeaRegId(Connection con, String actividadId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT TIPO_ID, ACTIVIDAD_ID," +
					" ACTIVIDAD_NOMBRE, VALOR, NIVEL_ID, MODIFICABLE " +
					" FROM ENOC.HCA_ACTIVIDAD" + 
					" WHERE ACTIVIDAD_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaActividadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	

	}

}