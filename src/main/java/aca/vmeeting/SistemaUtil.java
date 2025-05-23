/**
 * 
 */
package aca.vmeeting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * @author elifo
 *
 */
public class SistemaUtil {
	public static ArrayList<Sistema> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Sistema> lis	= new ArrayList<Sistema>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT ID, NOMBRE, IS_LOCAL" +
 				" FROM SISTEMA "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Sistema sistema = new Sistema();
				sistema.mapeaReg(rs);
				lis.add(sistema);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.SistemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lis;
	}
}
