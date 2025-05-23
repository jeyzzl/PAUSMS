/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class CertCiclo {
	private String planId;
	private String cicloId;
	private String titulo;
	private String fst;
	private String fsp;
	private String creditos;
	
	public CertCiclo(){
		planId		= "";
		cicloId		= "";
		titulo		= "";
		fst			= "";
		fsp			= "";
		creditos	= "";
	}

	/**
	 * @return the cicloId
	 */
	public String getCicloId() {
		return cicloId;
	}

	/**
	 * @param cicloId the cicloId to set
	 */
	public void setCicloId(String cicloId) {
		this.cicloId = cicloId;
	}

	/**
	 * @return the fst
	 */
	public String getFst() {
		return fst;
	}

	/**
	 * @param fst the fst to set
	 */
	public void setFst(String fst) {
		this.fst = fst;
	}

	/**
	 * @return the fsp
	 */
	public String getFsp() {
		return fsp;
	}

	/**
	 * @param fsp the fsp to set
	 */
	public void setFsp(String fsp) {
		this.fsp = fsp;
	}

	/**
	 * @return the creditos
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		planId		= rs.getString("PLAN_ID");
		cicloId		= rs.getString("CICLO_ID");
		titulo		= rs.getString("TITULO")==null?"":rs.getString("TITULO");
		fst			= rs.getString("FST")==null?"":rs.getString("FST");
		fsp			= rs.getString("FSP")==null?"":rs.getString("FSP");
		creditos	= rs.getString("CREDITOS")==null?"":rs.getString("CREDITOS");
	}
	
	public void mapeaRegId(Connection conn, String planId, String cicloId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO_ID," +
					" TITULO, FST, FSP, CREDITOS" +
					" FROM ENOC.CERT_CICLO" + 
					" WHERE PLAN_ID = ?" +
					" AND CICLO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, planId);
			ps.setString(2, cicloId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|existeReg|:"+ex);
		}finally{
			rs.close();
			ps.close();
		}
		
	}
}