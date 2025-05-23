/**
 * 
 */
package aca.vmeeting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class Sistema {
	private int id;
	private String nombre;
	private boolean isLocal;
	
	public Sistema(){
		id		= 0;
		nombre	= "";
		isLocal	= false;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the isLocal
	 */
	public boolean isLocal() {
		return isLocal;
	}

	/**
	 * @param isLocal the isLocal to set
	 */
	public void setIsLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO SISTEMA"+ 
				"(ID, NOMBRE, IS_LOCAL)"+
				" VALUES(?, ?, ?)");			
			ps.setInt(1, id);
			ps.setString(2, nombre);
			ps.setBoolean(3, isLocal);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{ 		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE SISTEMA"+ 
				" SET"+		
				" NOMBRE = ?," +
				" IS_LOCAL = ?" +
				" WHERE ID = ?");
				
			ps.setString(1, nombre);
			ps.setBoolean(2, isLocal);
			ps.setInt(3, id);
			 			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false; 			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SISTEMA"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
		id		= rs.getInt("ID");
		nombre	= rs.getString("NOMBRE");
		isLocal	= rs.getBoolean("IS_LOCAL");
	}
	
	public void mapeaRegId( Connection conn, int id) throws SQLException, IOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" ID, NOMBRE, IS_LOCAL" +
	 			" FROM SISTEMA WHERE ID = ?"); 
	 		ps.setInt(1, id);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;		
		
		try{
			ps = conn.prepareStatement("SELECT ID FROM SISTEMA"+ 
				" WHERE ID = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int maximoReg(Connection conn) throws SQLException{
		int maximo 				= 1;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ID)+1 MAXIMO FROM SISTEMA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getInt("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.Sistema|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}
