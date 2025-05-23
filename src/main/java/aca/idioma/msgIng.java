package aca.idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class msgIng {

	private String clave;
	private String valor;
	
	public msgIng(){
		clave 	= "";
		valor 	= "";
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" INSERT INTO ENOC.MSG_EN(CLAVE, VALOR) "
									 + " VALUES(?, ?)");
			
			ps.setString(1, clave);
			ps.setString(2, valor);			
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" UPDATE MSG_EN "
									 + " SET CLAVE = ?, "
									 + " VALOR = ? "
									 + " WHERE CLAVE = ?");
			
			ps.setString(1, clave);
			ps.setString(2, valor);
			ps.setString(3, clave);
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" DELETE FROM ENOC.MSG_EN "
									 + " WHERE CLAVE = ? ");
			
			ps.setString(1, clave);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public boolean deleteAllReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(" DELETE FROM ENOC.MSG_EN ");
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|deleteAllReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		clave		= rs.getString("CLAVE");
		valor		= rs.getString("VALOR");
	}
	
	public void mapeaRegId(Connection conn, String valor) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT CLAVE, VALOR FROM ENOC.MSG_EN" + 
					" WHERE VALOR = ?)");
			
			ps.setString(1, valor);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.msgIng|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
}
