// Bean folio la tabla folio Adm_Alum_Req
package  aca.admision;

import java.sql.*;

public class AdmAlumPre{
	private String alumFolio;
	private String carreraId;
	private String folio;
	private String estado;
		
	public AdmAlumPre(){
		alumFolio	= "";
		carreraId 		= "";
		folio 			= "";
		estado 			= "";
	}

	/**
	 * @return the alumFolio
	 */
	public String getAlumFolio() {
		return alumFolio;
	}

	/**
	 * @param alumFolio the alumFolio to set
	 */
	public void setAlumFolio(String alumFolio) {
		this.alumFolio = alumFolio;
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_ALUMPRE"+
				"(ALUM_FOLIO, CARRERA_ID, FOLIO, ESTADO) "+
				"VALUES( ?, ?, TO_NUMBER(?,'99'),? )");
			ps.setString(1, alumFolio);
			ps.setString(2, carreraId);
			ps.setString(3, folio);
			ps.setString(4, estado);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumPre|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ALUMPRE" +
					" SET ESTADO = ?" +				
					" WHERE FOLIO = TO_NUMBER(?,'99')" +
					" AND CARRERA_ID = ? " +
					" AND ALUM_FOLIO = ?");	
			ps.setString(1,  estado);
			ps.setString(2,  folio);
			ps.setString(3,  carreraId);
			ps.setString(4,  alumFolio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumPre|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ALUMPRE"+
					" WHERE FOLIO = TO_NUMBER(?,'99') " +
					" AND CARRERA_ID = ?" +
					" AND ALUM_FOLIO = ?");
			ps.setString(1, folio);
			ps.setString(2, carreraId);
			ps.setString(3, alumFolio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumPre|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		alumFolio		= rs.getString("ALUM_FOLIO");
		carreraId 		= rs.getString("CARRERA_ID");
		folio			= rs.getString("FOLIO");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String alumFolio, String carreraId, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ALUM_FOLIO, CARRERA_ID, FOLIO, ESTADO "+
				" FROM SALOMON.ADM_ALUMPRE"+
				" WHERE FOLIO = TO_NUMBER(?,'99')" +
				" AND CARRERA_ID = ?" +
				" AND ALUM_FOLIO = ?");
			ps.setString(1, folio);
			ps.setString(2, carreraId);
			ps.setString(3, alumFolio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmAlumPre|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ALUMPRE"+
					" WHERE FOLIO = TO_NUMBER(?,'99')" +
					" AND CARRERA_ID = ?" +
					" AND ALUM_FOLIO = ?");
			ps.setString(1,folio);
			ps.setString(2,carreraId);
			ps.setString(3,alumFolio);
					
			
			rs = ps.executeQuery();
				if (rs.next()){
				ok = true;
			}else{
			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

}		


