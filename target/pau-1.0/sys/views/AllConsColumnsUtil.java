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
public class AllConsColumnsUtil {
	public ArrayList<AllConsColumns> getListParents(Connection conn, String tableName, String orden ) throws SQLException{
		
		ArrayList<AllConsColumns> lisConsColumn	= new ArrayList<AllConsColumns>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT OWNER, CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, POSITION"+
 			" FROM SYS.ALL_CONS_COLUMNS" +
 			" WHERE OWNER = '"+Config.OWNER+"'" +
 			" AND POSITION IS NOT NULL" +
 			" AND CONSTRAINT_NAME IN (SELECT R_CONSTRAINT_NAME FROM ALL_CONSTRAINTS" +
 									" WHERE OWNER = '"+Config.OWNER+"'" +
 									" AND TABLE_NAME = '"+tableName+"'" +
 									" AND CONSTRAINT_TYPE = 'R') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AllConsColumns acc = new AllConsColumns();
				acc.mapeaReg(rs);
				lisConsColumn.add(acc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - sys.vews.AllConstraintsUtil|getListParents|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisConsColumn;
	}
}