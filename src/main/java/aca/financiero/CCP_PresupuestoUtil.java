package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CCP_PresupuestoUtil {
	public static String getSuma(Connection conn, String porcentaje, String meses, String cuenta, String idEjercicio) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		String suma		 		= "";
		try{
			if(!meses.equals("")){
				ps = conn.prepareStatement("SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
						"AND ID_CTAMAYOR = '1.3.05' AND MES IN ("+meses+")");
			}else{
				ps = conn.prepareStatement("SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
						"AND ID_CTAMAYOR = '"+cuenta+"'");
			}
			rs= ps.executeQuery();	
			if(rs.next()){
				suma = rs.getString("SUMA");
			}
		}catch(Exception e){
			System.out.println("Error - aca.financiero.CCP_PresupuestoUtil|getSuma|:"+e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return suma;
	}
	
	public static String getSumaCcosto(Connection conn, String porcentaje, String meses, String cuenta, String idEjercicio, String ccosto) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		String suma		 		= "";
		try{
			if(!meses.equals("")){
				ps = conn.prepareStatement("SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = '"+ccosto+"' AND ID_EJERCICIO = '"+idEjercicio+"' " +
						"AND ID_CTAMAYOR = '"+cuenta+"' AND MES IN ("+meses+")");
			}else{
				ps = conn.prepareStatement("SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = '"+ccosto+"' AND ID_EJERCICIO = '"+idEjercicio+"' " +
						"AND ID_CTAMAYOR = '"+cuenta+"'");
			}
			rs= ps.executeQuery();	
			if(rs.next()){
				suma = rs.getString("SUMA");
			}
		}catch(Exception e){
			System.out.println("Error - aca.financiero.CCP_PresupuestoUtil|getSumaCcosto|:"+e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return suma;
	}
}
