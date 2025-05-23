package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalInvitadoUtil {    
	
	public SalInvitado mapeaRegId(Connection con, String salidaId, String folio) throws SQLException{
		
		SalInvitado invitado = new SalInvitado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = con.prepareStatement("SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO"
					+ " FROM ENOC.SAL_INVITADO"
					+ " WHERE SALIDA_ID = ?" );
			ps.setString(1, salidaId);
			rs = ps.executeQuery();
		
			if(rs.next()){		
				invitado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalInvitado|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return invitado;
	}
	
	public boolean existeReg(Connection conn, String salidaId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT SALIDA_ID FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?, '99999')");
			ps.setString(1, salidaId);	
						
			rs= ps.executeQuery();
			if(rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalInvitado|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public ArrayList<SalInvitado> lisInvitados(Connection conn, String salidaId, String orden ) throws SQLException{
		ArrayList<SalInvitado> lista 	= new ArrayList<SalInvitado>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO FROM ENOC.SAL_INVITADO WHERE SALIDA_ID ='"+salidaId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalInvitado invitado = new SalInvitado();				
				invitado.mapeaReg(rs);
				lista.add(invitado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalInvitado|lisInvitados|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return lista;
	}
}