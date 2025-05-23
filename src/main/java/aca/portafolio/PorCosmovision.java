//Bean del Catálogo de Documentos

package aca.portafolio;

import java.sql.*;

public class PorCosmovision {

	private String codigoPersonal;
	private String periodoId;
	private String filosofia;
	private String mision;
	private String vision;
	private String valores;
	private String reflexion;
	
	public PorCosmovision(){
		codigoPersonal 	= "";
		periodoId 		= "";
		filosofia 		= "";
		mision 			= "";
		vision 			= "";
		valores 		= "";
		reflexion 		= "";
	}	
	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getFilosofia() {
		return filosofia;
	}

	public void setFilosofia(String filosofia) {
		this.filosofia = filosofia;
	}

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getValores() {
		return valores;
	}

	public void setValores(String valores) {
		this.valores = valores;
	}

	public String getReflexion() {
		return reflexion;
	}

	public void setReflexion(String reflexion) {
		this.reflexion = reflexion;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_COSMOVISION( CODIGO_PERSONAL, PERIODO_ID, FILOSOFIA,  MISION, VISION, VALORES, REFLEXION) "+
				"VALUES( ?, ?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, filosofia);			
			ps.setString(4, mision);
			ps.setString(5, vision);
			ps.setString(6, valores);
			ps.setString(7, reflexion);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovision|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_COSMOVISION "+ 
				"SET FILOSOFIA = ?,  MISION = ?, VISION = ?, VALORES = ?, REFLEXION = ? WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? ");
			
			ps.setString(1, filosofia);
			ps.setString(2, mision);			
			ps.setString(3, vision);
			ps.setString(4, valores);
			ps.setString(5, reflexion);
			ps.setString(6, codigoPersonal);
			ps.setString(7, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovision|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM DANIEL.POR_COSMOVISION "+ 
				"WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovision|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		periodoId 			= rs.getString("PERIODO_ID");
		filosofia 			= rs.getString("FILOSOFIA");
		mision 				= rs.getString("MISION");
		vision 				= rs.getString("VISION");
		valores 			= rs.getString("VALORES");
		reflexion 			= rs.getString("REFLEXION");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String periodoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT *" +
					" FROM DANIEL.POR_COSMOVISION WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovision|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_COSMOVISION WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovision|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
		
}