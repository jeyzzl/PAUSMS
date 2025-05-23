package sys.views;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sys.general.Config;

/**
 * @author Elifo
 *
 */
public class AllConsColumns {
	private String owner;
	private String constraintName;
	private String tableName;
	private String columnName;
	private String position;
	
	public AllConsColumns(){
		owner			= "";
		constraintName	= "";
		tableName		= "";
		columnName		= "";
		position		= "";
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the constraintName
	 */
	public String getConstraintName() {
		return constraintName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException, IOException{
 		owner			= rs.getString("OWNER");
		constraintName	= rs.getString("CONSTRAINT_NAME");
		tableName		= rs.getString("TABLE_NAME");
		columnName		= rs.getString("COLUMN_NAME");
		position		= rs.getString("POSITION");
 	}
 	
 	public void mapeaRegId(Connection conn, String tableName, String columnName) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT OWNER, CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, POSITION"+
	 			" FROM SYS.ALL_CONS_COLUMNS" +
	 			" WHERE OWNER = '"+Config.OWNER+"'" +
				" AND TABLE_NAME = ?" +
				" AND COLUMN_NAME = ?" +
	 			" AND POSITION IS NOT NULL");
				
			ps.setString(1, tableName);
			ps.setString(2, columnName);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - sys.views.AllConsColumns|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
 	
 	public void mapeaRegId(Connection conn, String constraintName) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT OWNER, CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, POSITION"+
	 			" FROM SYS.ALL_CONS_COLUMNS" +
	 			" WHERE OWNER = '"+Config.OWNER+"'" +
				" AND CONSTRAINT_NAME = ?" +
	 			" AND POSITION IS NOT NULL");
				
			ps.setString(1, constraintName);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - sys.views.AllConsColumns|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
 	
 	public boolean existeReg(Connection conn, String tableName, String columnName) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT OWNER, CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, POSITION" +
 					" FROM SYS.ALL_CONS_COLUMNS"+
 					" WHERE OWNER = '"+Config.OWNER+"'" +
 					" AND TABLE_NAME = ?" +
 					" AND COLUMN_NAME = ?" +
 					" AND POSITION IS NOT NULL");
 			
 			ps.setString(1, tableName);
 			ps.setString(2, columnName);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - sys.views.AllConsColumns|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
}