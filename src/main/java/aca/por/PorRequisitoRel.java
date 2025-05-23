package aca.por;

import java.sql.*;

public class PorRequisitoRel {
	
	private String requisitoId;
	private String portafolioId;
	private String seccionId;
	
	
	public String getRequisitoId() {
		return requisitoId;
	}
	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}
	public String getPortafolioId() {
		return portafolioId;
	}
	public void setPortafolioId(String portafolioId) {
		this.portafolioId = portafolioId;
	}
	public String getSeccionId() {
		return seccionId;
	}
	public void setSeccionId(String seccionId) {
		this.seccionId = seccionId;
	}
	
	public boolean insertReg (Connection conn) throws Exception{
		
		PreparedStatement ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.POR_REQUISITO_REL( REQUISITO_ID, PORTAFOLIO_ID, SECCION_ID)"
					+ " VALUES( TO_NUMBER(?, '9999999'), TO_NUMBER(?, '999'),?)");
			ps.setString(1, requisitoId);
			ps.setString(2, portafolioId);
			ps.setString(3, seccionId);
			
			if (ps.executeUpdate()== 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		
		PreparedStatement ps 	= null;
		boolean ok				= false;
		
		try{
			ps=conn.prepareStatement("DELETE FROM ENOC.POR_REQUISITO_REL WHERE REQUISITO_ID = TO_NUMBER(?, '9999999')");
			ps.setString(1, requisitoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
		}catch (Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		requisitoId		= rs.getString("REQUISITO_ID");
		portafolioId	= rs.getString("PORTAFOLIO_ID");
		seccionId		= rs.getString("SECCION_ID");
	}
	
	public void mapeaRegId(Connection conn, String documentoId) throws SQLException{
		ResultSet rs 		 = null;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_ID, PORTAFOLIO_ID, SECCION_ID"
					+ "FROM ENOC.POR_REQUISITO_REL WHERE REQUISITO_ID = TO_NUMBER(?, '9999999')");
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			if(rs!=null) rs.close();			
			if(ps!=null) ps.close();
		}
	}
		
	public void mapeaRegId(Connection conn) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
			
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_ID, PORTAFOLIO_ID, SECCION_ID"
					+ " FROM ENOC.POR_REQUISITO_REL WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
		
	public boolean existeReg(Connection conn) throws SQLException{
			
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		boolean 		ok 		= false;
			
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_REQUISITO_REL WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1,requisitoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
			
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(REQUISITO_ID)+1) AS MAXIMO FROM ENOC.POR_REQUISITO_REL");
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if(maximo == null)
				maximo = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoRel|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
		
}
