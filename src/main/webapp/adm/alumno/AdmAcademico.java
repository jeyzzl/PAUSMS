package adm.alumno;
/*Autor Ery*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmAcademico {
	private String folio;
	private String modalidad;
	private String nivelId;
	private String carreraId;
	private String fecha;
		
	public AdmAcademico(){
		folio 			= "";
		modalidad 		= "";
		nivelId 		= "";
		carreraId		= "";
		fecha 			= "";
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
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}



	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}



	/**
	 * @return the nivelId
	 */
	public String getNivelId() {
		return nivelId;
	}



	/**
	 * @param nivelId the nivelId to set
	 */
	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}



	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_ACADEMICO"+ 
				"(FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, FECHA ) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'),TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'))");
			ps.setString(1, folio);
			ps.setString(2, modalidad);
			ps.setString(3, nivelId);
			ps.setString(4, carreraId);
			ps.setString(5, fecha);
	
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAcademico|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ACADEMICO " + 
					" SET MODALIDAD_ID = TO_NUMBER(?,'99'), " +
					" NIVEL_ID = TO_NUMBER(?,'99'), " +
					" CARRERA_ID = ?, " +
					" FECHA = TO_DATE(?,'DD/MM/YYYY') " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			
			ps.setString(1,  modalidad);
			ps.setString(2,  nivelId);
			ps.setString(3,  carreraId);
			ps.setString(4,  fecha);
			ps.setString(5,  folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAcademico|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ACADEMICO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAcademico|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		modalidad		= rs.getString("MODALIDAD_ID");
		nivelId 		= rs.getString("NIVEL_ID");
		carreraId		= rs.getString("CARRERA_ID");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA " +
				" FROM SALOMON.ADM_ACADEMICO "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ACADEMICO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
						
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