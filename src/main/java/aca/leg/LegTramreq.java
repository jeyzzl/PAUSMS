package aca.leg;

import java.sql.*;

public class LegTramreq {
	public String tramiteId;
	public String requisitoId;
	
	public LegTramreq(){
		tramiteId 		= "";
		requisitoId		= "";
	}

	/**
	 * @return the tramiteId
	 */
	public String getTramiteId() {
		return tramiteId;
	}

	/**
	 * @param tramiteId the tramiteId to set
	 */
	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	/**
	 * @return the requisitoId
	 */
	public String getRequisitoId() {
		return requisitoId;
	}

	/**
	 * @param requisitoId the requisitoId to set
	 */
	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}


	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		tramiteId		= 	rs.getString("TRAMITE_ID");
		requisitoId		= 	rs.getString("REQUISITO_ID");
	}
	
	public void mapeaRegId(Connection conn)	throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		try{
			ps	= conn.prepareStatement("SELECT TRAMITE_ID, REQUISITO_ID FROM ENOC.LEG_TRAMREQ WHERE TRAMITE_ID = TO_NUMBER(?,'99') AND REQUISITO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, tramiteId);
			ps.setString(2, requisitoId);
			rs	=	ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramreq|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	

}