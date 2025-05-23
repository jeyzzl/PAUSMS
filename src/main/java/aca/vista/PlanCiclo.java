/**
 * 
 */
package aca.vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author general
 *
 */
public class PlanCiclo {
	private String planId;
	private String ciclo;
	private String creditos;
	
	public PlanCiclo(){
		planId		= "";
		ciclo		= "";
		creditos	= "";
	}

	/**
	 * @return Returns the ciclo.
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @return Returns the creditos.
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @return Returns the planId.
	 */
	public String getPlanId() {
		return planId;
	}
	
public void mapeaReg(ResultSet rs ) throws SQLException{
		planId 			= rs.getString("PLAN_ID");
		ciclo	 		= rs.getString("CICLO");
		creditos 		= rs.getString("CREDITOS");
	}
	
	public void mapeaRegId( Connection conn, String planId, String ciclo) throws SQLException{
		
		
		ResultSet rs = null;
		
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO, CREDITOS" +
														" FROM ENOC.PLAN_CICLO WHERE PLAN_ID = ?" +
														" AND CICLO = ?");
			ps.setString(1,planId);
			ps.setInt(2, Integer.parseInt(ciclo));
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.PlanCiclo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public static int getNumCiclos(Connection conn, String planId) throws SQLException{
		ResultSet rs = null;
		int ciclos = 0;
		
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT MAX(CICLO) AS CICLO" +
														" FROM ENOC.PLAN_CICLO WHERE PLAN_ID = ?");
			ps.setString(1,planId);
			rs = ps.executeQuery();
			if (rs.next())
				ciclos = rs.getInt("CICLO");

		}catch(Exception ex){
			System.out.println("Error - aca.vista.PlanCiclo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ciclos;
	}
}