package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegRequisitosUtil {
	
	public boolean insertReg(Connection conn, LegRequisitos req) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_REQUISITO(REQUISITO_ID, REQUISITO_NOMBRE)"+ 
				" VALUES( TO_NUMBER(?,'99'), ?)");
			
			ps.setString(1, req.getRequisitoId());
			ps.setString(2, req.getRequisitoNombre());
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegRequisitos req ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_REQUISITO "+ 
				"SET REQUISITO_NOMBRE = ? "+
				"WHERE REQUISITO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, req.getRequisitoNombre());
			ps.setString(2, req.getRequisitoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String requisitoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_REQUISITO "+ 
				"WHERE REQUISITO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, requisitoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisito|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String requisitoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,requisitoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(MAX(REQUISITO_ID)+1), '1') MAXIMO FROM ENOC.LEG_REQUISITO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getRequisitoNombre(Connection conn, String requisitoId) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nombre 			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_NOMBRE FROM ENOC.LEG_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("REQUISITO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|getRequisitoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<LegRequisitos> getListAll(Connection conn, String orden) throws SQLException{		
		ArrayList<LegRequisitos> lisRequisito	= new ArrayList<LegRequisitos>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando="SELECT REQUISITO_ID, REQUISITO_NOMBRE FROM ENOC.LEG_REQUISITO "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegRequisitos requisito = new LegRequisitos();
				requisito.mapeaReg(rs);
				lisRequisito.add(requisito);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegRequisitosUtil|getListAll|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisRequisito;
	}
	
	public ArrayList<LegRequisitos> getListReqFaltan(Connection conn, String tramiteId, String orden) throws SQLException{
		ArrayList<LegRequisitos> lisRequisito	= new ArrayList<LegRequisitos>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando=" SELECT REQUISITO_ID, REQUISITO_NOMBRE FROM ENOC.LEG_REQUISITO" + 
					" WHERE REQUISITO_ID NOT IN " +
					"	(SELECT REQUISITO_ID FROM ENOC.LEG_TRAMREQ WHERE TRAMITE_ID = "+tramiteId+") "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegRequisitos requisito = new LegRequisitos();
				requisito.mapeaReg(rs);
				lisRequisito.add(requisito);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegRequisitosUtil|getListTramReq|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisRequisito;
	}
	
	public ArrayList<LegRequisitos> getListReqTramite(Connection conn, String tramiteId, String orden) throws SQLException{
		ArrayList<LegRequisitos> lisRequisito	= new ArrayList<LegRequisitos>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando=" SELECT REQUISITO_ID, REQUISITO_NOMBRE FROM ENOC.LEG_REQUISITO" + 
					" WHERE REQUISITO_ID IN " +
					"	(SELECT REQUISITO_ID FROM ENOC.LEG_TRAMREQ WHERE TRAMITE_ID = "+tramiteId+") "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegRequisitos requisito = new LegRequisitos();
				requisito.mapeaReg(rs);
				lisRequisito.add(requisito);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegRequisitosUtil|getListReqTramite|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisRequisito;
	}

}