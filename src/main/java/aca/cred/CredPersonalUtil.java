package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CredPersonalUtil {
	
	public boolean insertReg(Connection conn, CredPersonal per ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CRED_PERSONAL"+ 
				"(EVENTO_ID, CODIGO_PERSONAL, NOMBRE) "+
				"VALUES( ?,?,?)");
			ps.setString(1, per.getEventoId());
			ps.setString(2, per.getCodigoPersonal());
			ps.setString(3, per.getNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CredPersonal per ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CRED_PERSONAL"+ 
				" SET"+
				" NOMBRE = ? "+
				" WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ? ");
			ps.setString(1, per.getNombre());
			ps.setString(2, per.getEventoId());
			ps.setString(3, per.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String eventoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CredPersonal mapeaRegId( Connection conn) throws SQLException{
		
		CredPersonal per = new CredPersonal();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"EVENTO_ID, CODIGO_PERSONAL, NOMBRE " +
				"FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = TO_NUMBER(?,999) AND CODIGO_PERSONAL = ?"); 
			ps.setString(1, per.getEventoId());
			ps.setString(2, per.getCodigoPersonal());
			
			rs = ps.executeQuery();
			if (rs.next()){
				per.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return per;
	}
	
	public CredPersonal mapeaRegCodigoPersonal( Connection conn, String codigoPersonal, String eventoId) throws SQLException{
		
		CredPersonal per = new CredPersonal();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"EVENTO_ID, CODIGO_PERSONAL, NOMBRE " +
				"FROM ENOC.CRED_PERSONAL WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND EVENTO_ID = '"+eventoId+"'"); 
			
			rs = ps.executeQuery();
			if (rs.next()){
				per.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegCodigoPersonal|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return per;
	}
	
	public boolean existeReg(Connection conn, CredPersonal per) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID, CODIGO_PERSONAL, NOMBRE FROM ENOC.CRED_PERSONAL"
					+ " WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, per.getEventoId());
			ps.setString(2, per.getCodigoPersonal());
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maxReg(Connection conn, String eventoId) throws SQLException{
		
		ResultSet 		rs		= null;
		ResultSet 		rs2		= null;
		PreparedStatement ps	= null;		
		PreparedStatement ps2	= null;
		String maximo 			= null;
		
		try{
			ps = conn.prepareStatement(" SELECT COALESCE(MAX(TO_NUMBER(CODIGO_PERSONAL))+1,0) AS MAXIMO FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = ?");
			ps.setString(1, eventoId);			
			rs = ps.executeQuery();
			if (rs.next()){
				maximo = rs.getString("MAXIMO");
				if (maximo.equals("0")){
					ps2 = conn.prepareStatement("SELECT CODIGO_INICIAL FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = ?");
					ps2.setString(1, eventoId);			
					rs2 = ps2.executeQuery();
					if (rs2.next()){
						maximo = rs2.getString("CODIGO_INICIAL");
					}
				}
			}				
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String getNombre(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	public  String getNombre(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'");
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CredPersonal> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CredPersonal> lista		= new ArrayList<CredPersonal>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT NOMBRE, EVENTO_ID, CODIGO_PERSONAL FROM ENOC.CRED_PERSONAL "+orden; 

			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CredPersonal cp = new CredPersonal();
				cp.mapeaReg(rs);
				lista.add(cp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonalUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public ArrayList<CredPersonal> getListNombre(Connection conn, String eventoId) throws SQLException{
		
		ArrayList<CredPersonal> lista       = new ArrayList<CredPersonal>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT NOMBRE, EVENTO_ID, CODIGO_PERSONAL FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = '"+eventoId+"'"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CredPersonal cp = new CredPersonal();
				cp.mapeaReg(rs);
				lista.add(cp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonalUtil|getListNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
}
