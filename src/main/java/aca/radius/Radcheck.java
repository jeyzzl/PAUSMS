/**
 * 
 */
package aca.radius;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class Radcheck {
	private String id;
	private String username;
	private String attribute;
	private String op;
	private String value;
	
	public Radcheck(){
		id			= "";
		username	= "";
		attribute	= "";
		op			= "";
		value		= "";
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * @return the op
	 */
	public String getOp() {
		return op;
	}
	/**
	 * @param op the op to set
	 */
	public void setOp(String op) {
		this.op = op;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO RADCHECK(USERNAME, ATTRIBUTE, OP, VALUE) " + 
					"VALUES(?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, attribute);
			ps.setString(3, op);
			ps.setString(4, value);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE RADCHECK" +
					" SET USERNAME = ?," +
					" ATTRIBUTE = ?," +
					" OP = ?," +
					" VALUE = ?" +
					" WHERE ID = TO_NUMBER(?,'999999')");			 
			ps.setString(1, username);
			ps.setString(2, attribute);
			ps.setString(3, op);
			ps.setString(4, value);
			ps.setString(5, id);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM RADCHECK WHERE ID = TO_NUMBER(?,'999999')"); 
			ps.setString(1, id);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id  		= rs.getString("ID");
		username	= rs.getString("USERNAME");
		attribute	= rs.getString("ATTRIBUTE");
		op 			= rs.getString("OP");
		value		= rs.getString("VALUE");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps =null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT ID, USERNAME, ATTRIBUTE, OP, VALUE "+
					"FROM RADCHECK WHERE ID = TO_NUMBER(?, '999999') "); 
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM RADCHECK WHERE ID = TO_NUMBER(?, '999999') "); 
			ps.setString(1, id);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegUsername(Connection conn, String username) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ID, USERNAME, ATTRIBUTE, OP, VALUE FROM RADCHECK WHERE USERNAME = ?"); 
			ps.setString(1, username);
			rs= ps.executeQuery();		
			if(rs.next()){
				mapeaReg(rs);
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|existeRegUsername|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteRegUsername(Connection conn, String username) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM RADCHECK WHERE USERNAME = ?"); 
			ps.setString(1, username);			
			if ( ps.executeUpdate() >= 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.radius.Radcheck|deleteRegUsername|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
}
