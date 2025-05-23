package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegTramreqUtil {
	
	public boolean insertReg(Connection conn, LegTramreq tramite ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_TRAMREQ(TRAMITE_ID, REQUISITO_ID)"+
				" VALUES( TO_NUMBER(?,'99'), TO_NUMBER(?,'99') )");
			ps.setString(1, tramite.getTramiteId());
			ps.setString(2, tramite.getRequisitoId());
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramreq|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String tramiteId, String requisitoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_TRAMREQ"+ 
				" WHERE TRAMITE_ID= TO_NUMBER(?, '99')" +
				" AND REQUISITO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, tramiteId);
			ps.setString(2, requisitoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramitereq|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String tramiteId, String requisitoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_TRAMREQ WHERE TRAMITE_ID = TO_NUMBER(?,'99') AND REQUISITO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,tramiteId);
			ps.setString(2,requisitoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramreq|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(MAX(REQUISITO_ID)+1), '1') MAXIMO FROM ENOC.LEG_TRAMREQ"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramreq|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<LegTramreq> getListAll(Connection conn, String orden) throws SQLException{		
		ArrayList<LegTramreq> lisTramreq		= new ArrayList<LegTramreq>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando="SELECT TRAMITE_ID, REQUISITO_ID FROM ENOC.LEG_TRAMREQ "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegTramreq tramreq = new LegTramreq();
				tramreq.mapeaReg(rs);
				lisTramreq.add(tramreq);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegTramreqUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisTramreq;
	}

}