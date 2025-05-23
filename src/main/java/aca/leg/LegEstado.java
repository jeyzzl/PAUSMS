package aca.leg;

import java.sql.*;

public class LegEstado {

	public String estadoId;
	public String estadoNombre;
	
	public LegEstado(){
		estadoId 		= "";
		estadoNombre	= "";
	}

	/**
	 * @return the estadoId
	 */
	public String getEstadoId() {
		return estadoId;
	}

	/**
	 * @param estadoId the estadoId to set
	 */
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	/**
	 * @return the estadoNombre
	 */
	public String getEstadoNombre() {
		return estadoNombre;
	}

	/**
	 * @param estadoNombre the estadoNombre to set
	 */
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		estadoId		= 	rs.getString("ESTADO_ID");
		estadoNombre	= 	rs.getString("ESTADO_NOMBRE");
	}
	
	public void mapeaRegId(Connection conn, String estadoId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		try{
			ps	= conn.prepareStatement("SELECT ESTADO_ID, ESTADO_NOMBRE FROM ENOC.LEG_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, estadoId);
			rs	=	ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegEstado|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}