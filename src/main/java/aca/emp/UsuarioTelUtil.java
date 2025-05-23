package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UsuarioTelUtil {
	
	public boolean insertReg(Connection conn, UsuarioTel usuarioTel ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("INSERT INTO ENOC.USUARIO_TEL"+ 
				" (CODIGO_PERSONAL, CARGAS, MODALIDADES)"+
				" VALUES( ?, ?, ? )");
			
			ps.setString(1, usuarioTel.getCodigoPersonal());
			ps.setString(2, usuarioTel.getTelefono());
			ps.setString(3, usuarioTel.getExtension());
			ps.setString(4, usuarioTel.getLugar());
						
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	

	public boolean updateReg(Connection conn, UsuarioTel usuarioTel ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.USUARIO_TEL"+ 
				" SET "+
				" TELEFONO = ?,"+
				" EXTENSION = ?," +
				" LUGAR "+
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, usuarioTel.getTelefono());
			ps.setString(2, usuarioTel.getExtension());
			ps.setString(3, usuarioTel.getLugar());
			ps.setString(4, usuarioTel.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.USUARIO_TEL"+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public UsuarioTel mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		UsuarioTel usuarioTel = new UsuarioTel();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, TELEFONO, EXTENSION, LUGAR" +						
				" FROM ENOC.USUARIO_TEL WHERE CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				usuarioTel.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return usuarioTel;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.USUARIO_TEL "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getExtension(Connection conn, String codigoEmpleado) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String extension		= "-";
		
		try{
			ps = conn.prepareStatement("SELECT EXTENSION FROM ENOC.USUARIO_TEL WHERE CODIGO_PERSONAL = ? "); 
			
			ps.setString(1, codigoEmpleado);
			
			rs = ps.executeQuery();
			if (rs.next())
				extension = rs.getString("EXTENSION");
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|getExtension|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return extension;
	}
	
	public ArrayList<UsuarioTel> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<UsuarioTel> lisTel	= new ArrayList<UsuarioTel>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, TELEFONO, EXTENSION, LUGAR FROM ENOC.USUARIO_TEL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				UsuarioTel tel = new UsuarioTel();
				tel.mapeaReg(rs);
				lisTel.add(tel);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTel;
	}
	
}