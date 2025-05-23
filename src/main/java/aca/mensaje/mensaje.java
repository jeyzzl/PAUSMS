package aca.mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mensaje {
	 
	private String codigoPersonal;
	private String mensaje1;
	private String mensaje2;
 	
 	public mensaje(){
 		codigoPersonal		= "-";	
 		mensaje1			= "-";	
 		mensaje2			= "-";	
 	} 	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getMensaje1() {
		return mensaje1;
	}

	public void setMensaje1(String mensaje1) {
		this.mensaje1 = mensaje1;
	}

	public String getMensaje2() {
		return mensaje2;
	}

	public void setMensaje2(String mensaje2) {
		this.mensaje2 = mensaje2;
	}

	
	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MENSAJE(CODIGO_PERSONAL, MENSAJE1, MENSAJE2) VALUES(?,?,?) ");
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, mensaje1);	
 			ps.setString(3, mensaje2);	
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mensaje.mensaje|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENSAJE "+ 
					"SET MENSAJE1 = ?, MENSAJE2 = ? WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, mensaje1);
			ps.setString(2, mensaje2);
			ps.setString(3, codigoPersonal);
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mensaje.mensaje|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		mensaje1			= rs.getString("MENSAJE1");
		mensaje2			= rs.getString("MENSAJE2");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, MENSAJE1, MENSAJE2 FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");
				
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mensaje.Mensaje|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mensaje.mensaje|existeReg|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean deleteReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENSAJE "+ 
				" WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mensaje.mensaje|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
}
 	