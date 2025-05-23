package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FinMensajeUtil {
	
	
	public boolean insertReg(Connection conn, FinMensaje mensaje ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FIN_MENSAJE" + 
					" (ID, COMENTARIO, ESTADO, TIPO)" +
					" VALUES(TO_NUMBER(?, '99'), ?, ?, ? )");
			ps.setString(1, mensaje.getId());
			ps.setString(2, mensaje.getComentario());
			ps.setString(3, mensaje.getEstado());
			ps.setString(4, mensaje.getTipo());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, FinMensaje mensaje ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FIN_MENSAJE" + 
					" SET COMENTARIO = ?, " +
					" ESTADO = ?, " +
					" TIPO = ? " +
					" WHERE ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, mensaje.getComentario());
			ps.setString(2, mensaje.getEstado());
			ps.setString(3, mensaje.getTipo());
			ps.setString(4, mensaje.getId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String id ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FIN_MENSAJE" + 
					" WHERE ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, id);
		
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String id) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FIN_MENSAJE" + 
					" WHERE ID = TO_NUMBER(?, '99') ");
			
			ps.setString(1, id);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getMensaje(Connection conn, String id) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String mensaje			= "";
		
		try{
			ps = conn.prepareStatement("SELECT COMENTARIO FROM ENOC.FIN_MENSAJE" + 
					" WHERE ID = TO_NUMBER(?, '99') ");
			
			ps.setString(1, id);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				mensaje = rs.getString("COMENTARIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return mensaje;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ID)+1 MAXIMO FROM ENOC.FIN_MENSAJE"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinMensaje|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public ArrayList<FinMensaje> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FinMensaje> lisReligion = new ArrayList<FinMensaje>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ID, COMENTARIO, TIPO, ESTADO FROM ENOC.FIN_MENSAJE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FinMensaje religion = new FinMensaje();
				religion.mapeaReg(rs);
				lisReligion.add(religion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinMensajeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReligion;
	}
}
