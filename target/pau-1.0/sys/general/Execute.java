/**
 * 
 */
package sys.general;

import java.sql.*;

/**
 * @author Elifo
 *
 */
public class Execute {
	public static int update(Connection conn, String query) throws SQLException{
		PreparedStatement ps	= null;
		int rowsUpdated			= 0;
		try{
			ps = conn.prepareStatement(query);
			if (ps.executeUpdate() == 1){
				rowsUpdated = ps.getUpdateCount();
			}else{
				rowsUpdated = -1;
			}
		}catch(Exception e){
			System.out.println("Error - SQL Editor:"+e);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return rowsUpdated;
	}
}