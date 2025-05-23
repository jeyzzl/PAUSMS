/**
 * 
 */
package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class BecParametroUtil {
	
	public boolean insertReg(Connection conn, BecParametro becParametro) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_PARAMETRO"+ 
				"(PREPA_INICIO, PREPA_FINAL, PREGRADO_INICIO, PREGRADO_FINAL" +
				" VALUES( TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'))");
					
			ps.setString(1, becParametro.getPrepaInicio());
			ps.setString(2, becParametro.getPrepaFinal());
			ps.setString(3, becParametro.getPregradoInicio());
			ps.setString(4, becParametro.getPregradoFinal());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecParametroUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecParametro becParametro) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PARAMETRO"+ 
				" SET PREPA_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" PREPA_FINAL = TO_DATE(?, 'DD/MM/YYYY'), "+
				" PREGRADO_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
				" PREGRADO_FINAL = TO_DATE(?, 'DD/MM/YYYY')");
			
			ps.setString(1, becParametro.getPrepaInicio());
			ps.setString(2, becParametro.getPrepaFinal());
			ps.setString(3, becParametro.getPregradoInicio());
			ps.setString(4, becParametro.getPregradoFinal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecParametroUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static  HashMap<Integer, String> getMapFechas(Connection conn)throws SQLException{
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT TO_CHAR(PREPA_INICIO, 'DD/MM/YYYY') AS PREPA_INICIO,TO_CHAR(PREPA_FINAL, 'DD/MM/YYYY') AS PREPA_FINAL," +
					"TO_CHAR(PREGRADO_INICIO, 'DD/MM/YYYY') AS PREGRADO_INICIO, TO_CHAR(PREGRADO_FINAL, 'DD/MM/YYYY') AS PREGRADO_FINAL " +
					"FROM ENOC.BEC_PARAMETRO";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(1, rs.getString("PREPA_INICIO"));
				map.put(2, rs.getString("PREPA_FINAL"));
				map.put(3, rs.getString("PREGRADO_INICIO"));
				map.put(4, rs.getString("PREGRADO_FINAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecParametroUtil|getMapFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
	
}