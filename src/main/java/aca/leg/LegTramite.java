package aca.leg;

import java.sql.*;

public class LegTramite {

	public String tramiteId;
	public String tramiteNombre;
	public String costo;
	
	public LegTramite(){
		tramiteId 		= "";
		tramiteNombre	= "";
		costo 			= "";
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
	 * @return the tramiteNombre
	 */
	public String getTramiteNombre() {
		return tramiteNombre;
	}

	/**
	 * @param tramiteNombre the tramiteNombre to set
	 */
	public void setTramiteNombre(String tramiteNombre) {
		this.tramiteNombre = tramiteNombre;
	}

	/**
	 * @return the costo
	 */
	public String getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(String costo) {
		this.costo = costo;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		tramiteId		= 	rs.getString("TRAMITE_ID");
		tramiteNombre	= 	rs.getString("TRAMITE_NOMBRE");
		costo			= 	rs.getString("COSTO");
	}
	
	public void mapeaRegId(Connection conn, String tramiteId)	throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		try{
			ps	= conn.prepareStatement("SELECT TRAMITE_ID, TRAMITE_NOMBRE, COSTO FROM ENOC.LEG_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'99999.99')"); 
			ps.setString(1, tramiteId);
			rs	=	ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.LEG_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'99999.99')"); 
			ps.setString(1,tramiteId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(MAX(TRAMITE_ID)+1), '1') MAXIMO FROM ENOC.LEG_TRAMITE"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getTramiteNombre(Connection conn, String tramiteId) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String nombre 			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT TRAMITE_NOMBRE AND COSTO FROM ENOC.LEG_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'99999.99')"); 
			ps.setString(1, tramiteId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("TRAMITE_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegTramite|getEstadoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

}