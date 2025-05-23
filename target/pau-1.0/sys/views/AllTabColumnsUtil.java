/**
 * 
 */
package sys.views;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sys.general.*;

/**
 * @author Elifo
 *
 */
public class AllTabColumnsUtil {
	public ArrayList<AllTabColumns> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AllTabColumns> lisBD	= new ArrayList<AllTabColumns>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+
 			" OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER, DATA_LENGTH, DATA_PRECISION," +
 			" DATA_SCALE, NULLABLE, COLUMN_ID, DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE," +
 			" DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH," +
 			" GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED, V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM"+
 			" FROM SYS.ALL_TAB_COLUMNS" +
 			" WHERE OWNER = '"+Config.OWNER+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AllTabColumns atc = new AllTabColumns();
				atc.mapeaReg(rs);
				lisBD.add(atc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - sys.vews.AllTabColumnsUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBD;
	}
	
	public ArrayList<AllTabColumns> getListTable(Connection conn, String tableName, String orden ) throws SQLException{
		
		ArrayList<AllTabColumns> lisBD	= new ArrayList<AllTabColumns>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT"+
 			" OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER, DATA_LENGTH, DATA_PRECISION," +
 			" DATA_SCALE, NULLABLE, COLUMN_ID, DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE," +
 			" DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH," +
 			" GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED, V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM"+
 			" FROM SYS.ALL_TAB_COLUMNS" +
 			" WHERE OWNER = '"+Config.OWNER+"'" +
 			" AND TABLE_NAME = '"+tableName+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AllTabColumns atc = new AllTabColumns();
				atc.mapeaReg(rs);
				lisBD.add(atc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - sys.vews.AllTabColumnsUtil|getListTable|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisBD;
	}
}