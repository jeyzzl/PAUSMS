// Bean folio la tabla folio Adm_Contacto
package  adm.alumno;

import java.sql.*;

public class AdmAlumPre{	
	private String alumFolio;
	private String carreraId;
	private String folio;
	private String estado;
		
	public AdmAlumPre(){
		alumFolio 	= "";
		carreraId 	= "";
		folio 		= ""; 
		estado 		= ""; 
		
	}
	
	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return alumFolio;
	}
	
	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.alumFolio = codigoPersonal;
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



	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ALUMPRE " + 
					"SET ESTADO = ? " +				
					"WHERE ALUM_FOLIO = TO_NUMBER(?,'99999999')" +
					"AND CARRERA_ID = ? " +
					"AND FOLIO = TO_NUMBER(?,'99')");
			
			ps.setString(1,  estado);
			ps.setString(2,  alumFolio);
			ps.setString(3,  carreraId);
			ps.setString(4,  folio);
						
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
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
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ALUMPRE "+ 
					"WHERE ALUM_FOLIO = TO_NUMBER(?,'99999999') " +
					"AND CARRERA_ID = ?" +
					"AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, alumFolio);
			ps.setString(2, carreraId);
			ps.setString(3, folio);
			
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
		alumFolio 			= rs.getString("ALUM_FOLIO");
		carreraId 			= rs.getString("CARRERA_ID");
		folio				= rs.getString("FOLIO");
		estado				= rs.getString("ESTADO");
		
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARRERA_ID, FOLIO,ESTADO  "+
			"FROM SALOMON.ADM_ALUMPRE "+ 
			"WHERE ALUM_FOLIO  = TO_NUMBER(?,'99999999') " +
			"AND CARRERA_ID = ?" +
			"AND FOLIO = TO_NUMBER(?,'99')");
		ps.setString(1, alumFolio);
		ps.setString(2, carreraId);
		ps.setString(3, folio);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ALUMPRE "+ 
					"WHERE ALUM_FOLIO = TO_NUMBER(?,'99999999')" +
					"AND CARRERA_ID = ? " +
					"AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1,alumFolio);
			ps.setString(2,carreraId);
			ps.setString(3,folio);
			
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
	
	public String maximoReg(Connection conn ) throws SQLException{
		PreparedStatement ps		= null;
		ResultSet 		rs			= null;
		String			maximo		= "1";
		
		try{
			System.out.println(carreraId);	
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM SALOMON.ADM_ALUMPRE "+ 
					"WHERE ALUM_FOLIO = TO_NUMBER(?,'99999999')" +
					"AND CARRERA_ID = ?");			
			ps.setString(1,alumFolio);
			ps.setString(1,carreraId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
}		