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
public class AllAllTablesUtil {
	public ArrayList<AllAllTables> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AllAllTables> lisTables	= new ArrayList<AllAllTables>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT"+
 			" OWNER, TABLE_NAME, TABLESPACE_NAME, CLUSTER_NAME, IOT_NAME," +
 			" STATUS, PCT_FREE, PCT_USED, INI_TRANS, MAX_TRANS," +
 			" INITIAL_EXTENT, NEXT_EXTENT, MIN_EXTENTS, MAX_EXTENTS, PCT_INCREASE," +
 			" FREELISTS, FREELIST_GROUPS, LOGGING, BACKED_UP, NUM_ROWS," +
 			" BLOCKS, EMPTY_BLOCKS, AVG_SPACE, CHAIN_CNT, AVG_ROW_LEN," +
 			" AVG_SPACE_FREELIST_BLOCKS, NUM_FREELIST_BLOCKS, DEGREE, INSTANCES, CACHE," +
 			" TABLE_LOCK, SAMPLE_SIZE, LAST_ANALYZED, PARTITIONED, IOT_TYPE," +
 			" OBJECT_ID_TYPE, TABLE_TYPE_OWNER, TABLE_TYPE, TEMPORARY, SECONDARY," +
 			" NESTED, BUFFER_POOL, ROW_MOVEMENT, GLOBAL_STATS, USER_STATS," +
 			" DURATION, SKIP_CORRUPT, MONITORING, CLUSTER_OWNER, DEPENDENCIES," +
 			" COMPRESSION, DROPPED"+
 			" FROM SYS.ALL_ALL_TABLES WHERE OWNER = '"+Config.OWNER+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AllAllTables aat = new AllAllTables();
				aat.mapeaReg(rs);
				lisTables.add(aat);
			}
			
		}catch(Exception ex){
			System.out.println("Error - sys.vews.AllAllTablesUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTables;
	}
}