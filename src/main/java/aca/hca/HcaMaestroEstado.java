/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrador
 *
 */
public class HcaMaestroEstado {
	private String codigoPersonal;
	private String cargaId;
	private String tSemanal;
	private String tSemestral;
	private String estado;
	
	public HcaMaestroEstado(){
		codigoPersonal	= "";
		cargaId			= "";
		tSemanal		= "";
		tSemestral		= "";
		estado			= "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	/**
	 * @return the tSemanal
	 */
	public String getTSemanal() {
		return tSemanal;
	}

	/**
	 * @param semanal the tSemanal to set
	 */
	public void setTSemanal(String semanal) {
		tSemanal = semanal;
	}

	/**
	 * @return the tSemestral
	 */
	public String getTSemestral() {
		return tSemestral;
	}

	/**
	 * @param semestral the tSemestral to set
	 */
	public void setTSemestral(String semestral) {
		tSemestral = semestral;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cargaId			= rs.getString("CARGA_ID");
		tSemanal		= rs.getString("T_SEMANAL");
		tSemestral		= rs.getString("T_SEMESTRAL");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String cargaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO FROM ENOC.HCA_MAESTRO_ESTADO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}