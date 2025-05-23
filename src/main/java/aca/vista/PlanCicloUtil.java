/**
 * 
 */
package aca.vista;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author general
 *
 */
public class PlanCicloUtil {
	public ArrayList<PlanCiclo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<PlanCiclo> lisCiclo	= new ArrayList<PlanCiclo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CICLO, CREDITOS FROM ENOC.PLAN_CICLO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				PlanCiclo pc = new PlanCiclo();
				pc.mapeaReg(rs);
				lisCiclo.add(pc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.PlanCicloUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiclo;
	}
	
	public ArrayList<PlanCiclo> getListCiclosPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<PlanCiclo> lisCiclo	= new ArrayList<PlanCiclo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CICLO, CREDITOS FROM ENOC.PLAN_CICLO WHERE PLAN_ID = '"+planId+"' AND CREDITOS != 0 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				PlanCiclo pc = new PlanCiclo();
				pc.mapeaReg(rs);
				lisCiclo.add(pc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.PlanCicloUtil|getListCiclosPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiclo;
	}
}