package aca.leg;

import java.sql.*;

public class LegRequisitos {
	public String requisitoId;
	public String requisitoNombre;
	
	public LegRequisitos(){
		requisitoId 		= "";
		requisitoNombre		= "";
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

	/**
	 * @return the requisitoNombre
	 */
	public String getRequisitoNombre() {
		return requisitoNombre;
	}

	/**
	 * @param requisitoNombre the requisitoNombre to set
	 */
	public void setRequisitoNombre(String requisitoNombre) {
		this.requisitoNombre = requisitoNombre;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		requisitoId		= 	rs.getString("REQUISITO_ID");
		requisitoNombre	= 	rs.getString("REQUISITO_NOMBRE");
	}
	
	public void mapeaRegId(Connection conn, String requisitoId)	throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		try{
			ps	= conn.prepareStatement("SELECT REQUISITO_ID, REQUISITO_NOMBRE FROM ENOC.LEG_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, requisitoId);
			rs	=	ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegRequisitos|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	

}