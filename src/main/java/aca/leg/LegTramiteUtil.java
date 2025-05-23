package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegTramiteUtil {
	
	public boolean insertReg(Connection conn, LegTramite tramite ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.LEG_TRAMITE(TRAMITE_ID, TRAMITE_NOMBRE, COSTO)"+
				" VALUES( TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99999.99'))");
			ps.setString(1, tramite.getTramiteId());
			ps.setString(2, tramite.getTramiteNombre());
			ps.setString(3, tramite.getCosto());
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, LegTramite tramite ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.LEG_TRAMITE "+ 
				"SET TRAMITE_NOMBRE = ?, " +
				"COSTO = TO_NUMBER(?,'99999.99') "+
				"WHERE TRAMITE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, tramite.getTramiteNombre());
			ps.setString(2, tramite.getCosto());
			ps.setString(3, tramite.getTramiteId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String tramiteId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.LEG_TRAMITE "+ 
				"WHERE TRAMITE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, tramiteId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<LegTramite> getListAll(Connection conn, String orden) throws SQLException{		
		ArrayList<LegTramite> lisTramite	= new ArrayList<LegTramite>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando="SELECT TRAMITE_ID, TRAMITE_NOMBRE, COSTO FROM ENOC.LEG_TRAMITE "+orden; 
			rs	=	st.executeQuery(comando);
			while (rs.next()){
				LegTramite tramite = new LegTramite();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegTramiteUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisTramite;
	}

}