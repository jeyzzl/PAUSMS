/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author etorres
 *
 */
public class HcaRango {
	private String nivelId;
	private String modalidadId;
	private String rangoId;
	private String rangoIni;
	private String rangoFin;
	private String valor;
	
	public HcaRango(){
		nivelId			= "";
		modalidadId		= "";
		rangoId			= "";
		rangoIni		= "";
		rangoFin		= "";
		valor			= "";
	}
	
	/**
	 * @return the modalidadId
	 */
	public String getModalidadId() {
		return modalidadId;
	}


	/**
	 * @param modalidadId the modalidadId to set
	 */
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
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
	 * @return the rangoFin
	 */
	public String getRangoFin() {
		return rangoFin;
	}


	/**
	 * @param rangoFin the rangoFin to set
	 */
	public void setRangoFin(String rangoFin) {
		this.rangoFin = rangoFin;
	}

	/**
	 * @return the rangoId
	 */
	public String getRangoId() {
		return rangoId;
	}

	/**
	 * @param rangoId the rangoId to set
	 */
	public void setRangoId(String rangoId) {
		this.rangoId = rangoId;
	}

	/**
	 * @return the rangoIni
	 */
	public String getRangoIni() {
		return rangoIni;
	}

	/**
	 * @param rangoIni the rangoIni to set
	 */
	public void setRangoIni(String rangoIni) {
		this.rangoIni = rangoIni;
	}

	/**
	 * @return the valorel
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		nivelId			= rs.getString("NIVEL_ID");
		modalidadId		= rs.getString("MODALIDAD_ID");
		rangoId			= rs.getString("RANGO_ID");
		rangoIni		= rs.getString("RANGO_INI");
		rangoFin		= rs.getString("RANGO_FIN");
		valor			= rs.getString("VALOR");
	}
	
	public void mapeaRegId(Connection con, String nivelId, String modalidadId, String rangoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT VALOR, RANGO_INI, RANGO_FIN, RANGO_ID, MODALIDAD_ID, NIVEL_ID FROM ENOC.HCA_RANGO" + 
					" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
					" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
					" AND RANGO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			ps.setString(3, rangoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}