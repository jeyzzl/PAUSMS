/**
 * 
 */
package sys.views;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sys.general.Config;

/**
 * @author Elifo
 *
 */
public class AllConstraintsUtil {
	public ArrayList<AllConstraints> getListTable(Connection conn, String tableName, String orden ) throws SQLException{
		
		ArrayList<AllConstraints> lisConstraints	= new ArrayList<AllConstraints>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT"+
 			" OWNER, CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME, SEARCH_CONDITION," +
 			" R_OWNER, R_CONSTRAINT_NAME, DELETE_RULE, STATUS, DEFERRABLE," +
 			" DEFERRED, VALIDATED, GENERATED, BAD, RELY," +
 			" LAST_CHANGE, INDEX_OWNER, INDEX_NAME, INVALID, VIEW_RELATED"+
 			" FROM SYS.ALL_CONSTRAINTS" +
 			" WHERE OWNER = '"+Config.OWNER+"'" +
 			" AND TABLE_NAME = '"+tableName+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AllConstraints ac = new AllConstraints();
				ac.mapeaReg(rs);
				lisConstraints.add(ac);
			}
			
		}catch(Exception ex){
			System.out.println("Error - sys.vews.AllConstraintsUtil|getListTable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisConstraints;
	}
}