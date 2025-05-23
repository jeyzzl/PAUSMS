/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class BajaPasoUtil {
	
	public BajaPaso mapeaRegId(Connection conn, String pasoId) throws SQLException{
		BajaPaso bajaPaso = new BajaPaso();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PASO_ID, PASO_NOMBRE" +
					" FROM ENOC.BAJA_PASO" + 
					" WHERE PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				bajaPaso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaPasoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return bajaPaso;
	}
	
	public ArrayList<BajaPaso> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<BajaPaso> lisPaso 	= new ArrayList<BajaPaso>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PASO_ID, PASO_NOMBRE FROM ENOC.BAJA_PASO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BajaPaso bp= new BajaPaso();
				bp.mapeaReg(rs);
				lisPaso.add(bp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaPasoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPaso;
	}
}