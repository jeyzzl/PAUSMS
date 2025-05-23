/**
 * 
 */
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
public class AllConstraints {
	private String owner;
	private String constraintName;
	private String constraintType;
	private String tableName;
	private String searchCondition;
	private String rOwner;
	private String rConstraintName;
	private String deleteRule;
	private String status;
	private String deferrable;
	private String deferred;
	private String validated;
	private String generated;
	private String bad;
	private String rely;
	private String lastChange;
	private String indexOwner;
	private String indexName;
	private String invalid;
	private String viewRelated;
	
	public AllConstraints(){
		owner				= "";
		constraintName		= "";
		constraintType		= "";
		tableName			= "";
		searchCondition		= "";
		rOwner				= "";
		rConstraintName		= "";
		deleteRule			= "";
		status				= "";
		deferrable			= "";
		deferred			= "";
		validated			= "";
		generated			= "";
		bad					= "";
		rely				= "";
		lastChange			= "";
		indexOwner			= "";
		indexName			= "";
		invalid				= "";
		viewRelated			= "";
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
	 * @return the constraintType
	 */
	public String getConstraintType() {
		return constraintType;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the searchCondition
	 */
	public String getSearchCondition() {
		return searchCondition;
	}

	/**
	 * @return the rOwner
	 */
	public String getROwner() {
		return rOwner;
	}

	/**
	 * @return the rConstraintName
	 */
	public String getRConstraintName() {
		return rConstraintName;
	}

	/**
	 * @return the deleteRule
	 */
	public String getDeleteRule() {
		return deleteRule;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the deferrable
	 */
	public String getDeferrable() {
		return deferrable;
	}

	/**
	 * @return the deferred
	 */
	public String getDeferred() {
		return deferred;
	}

	/**
	 * @return the validated
	 */
	public String getValidated() {
		return validated;
	}

	/**
	 * @return the generated
	 */
	public String getGenerated() {
		return generated;
	}

	/**
	 * @return the bad
	 */
	public String getBad() {
		return bad;
	}

	/**
	 * @return the rely
	 */
	public String getRely() {
		return rely;
	}

	/**
	 * @return the lastChange
	 */
	public String getLastChange() {
		return lastChange;
	}

	/**
	 * @return the indexOwner
	 */
	public String getIndexOwner() {
		return indexOwner;
	}

	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * @return the invalid
	 */
	public String getInvalid() {
		return invalid;
	}

	/**
	 * @return the viewRelated
	 */
	public String getViewRelated() {
		return viewRelated;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		owner				= rs.getString("OWNER");
		constraintName		= rs.getString("CONSTRAINT_NAME");
		constraintType		= rs.getString("CONSTRAINT_TYPE");
		tableName			= rs.getString("TABLE_NAME");
		searchCondition		= rs.getString("SEARCH_CONDITION");
		rOwner				= rs.getString("R_OWNER");
		rConstraintName		= rs.getString("R_CONSTRAINT_NAME");
		deleteRule			= rs.getString("DELETE_RULE");
		status				= rs.getString("STATUS");
		deferrable			= rs.getString("DEFERRABLE");
		deferred			= rs.getString("DEFERRED");
		validated			= rs.getString("VALIDATED");
		generated			= rs.getString("GENERATED");
		bad					= rs.getString("BAD");
		rely				= rs.getString("RELY");
		lastChange			= rs.getString("LAST_CHANGE");
		indexOwner			= rs.getString("INDEX_OWNER");
		indexName			= rs.getString("INDEX_NAME")==null?"":rs.getString("INDEX_NAME");
		invalid				= rs.getString("INVALID");
		viewRelated			= rs.getString("VIEW_RELATED");
 	}
 	
 	public void mapeaRegId( Connection conn, String constraintName) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" OWNER, CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME, SEARCH_CONDITION," +
	 			" R_OWNER, R_CONSTRAINT_NAME, DELETE_RULE, STATUS, DEFERRABLE," +
	 			" DEFERRED, VALIDATED, GENERATED, BAD, RELY," +
	 			" LAST_CHANGE, INDEX_OWNER, INDEX_NAME, INVALID, VIEW_RELATED"+
	 			" FROM SYS.ALL_CONSTRAINTS" +
	 			" WHERE OWNER = '"+Config.OWNER+"'" +
	 			" AND CONSTRAINT_NAME = ?");
	 		
	 		ps.setString(1, constraintName);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - sys.views.AllConstraints|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
 	
 	public boolean isPK(Connection conn, String tableName, String columnName) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM SYS.ALL_CONSTRAINTS" +
 					" WHERE CONSTRAINT_NAME IN (SELECT CONSTRAINT_NAME FROM SYS.ALL_CONS_COLUMNS" +
						 					" WHERE TABLE_NAME = ?" +
						 					" AND COLUMN_NAME = ?)" +
 					" AND CONSTRAINT_TYPE = 'P'");
 			
 			ps.setString(1, tableName);
 			ps.setString(2, columnName);
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				mapeaReg(rs);
 				ok = true;
 			}else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - sys.views.AllConstraints|isPK|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean isFK(Connection conn, String tableName, String columnName) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM SYS.ALL_CONSTRAINTS" +
 					" WHERE CONSTRAINT_NAME IN (SELECT CONSTRAINT_NAME FROM SYS.ALL_CONS_COLUMNS" +
						 					" WHERE TABLE_NAME = ?" +
						 					" AND COLUMN_NAME = ?)" +
 					" AND CONSTRAINT_TYPE = 'R'");
 			
 			ps.setString(1, tableName);
 			ps.setString(2, columnName);
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				mapeaReg(rs);
 				ok = true;
 			}else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - sys.views.AllConstraints|isPK|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
}