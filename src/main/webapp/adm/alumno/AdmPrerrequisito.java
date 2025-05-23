// Bean folio la tabla folio Adm_Contacto
package  adm.alumno;

import java.sql.*;

public class AdmPrerrequisito{	
	private String carreraId;
	private String folio;
	private String nombre;
		
	public AdmPrerrequisito(){
		carreraId 			= "";
		folio 			= "";
		nombre 			= ""; 
		
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_PRERREQUISITO"+ 
				"(CARRERA_ID, FOLIO, NOMBRE,) "+
				"VALUES( ?, TO_NUMBER(?,'9999999'),? )");
			ps.setString(1, carreraId);
			ps.setString(2, folio);
			ps.setString(3, nombre);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPrerrequisito|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_PRERREQUISITO " + 
					"SET NOMBRE = ? " +				
					"WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					"AND CARRERA_ID = ? ");
			
			ps.setString(1,  nombre);
			ps.setString(2,  folio);
			ps.setString(3,  carreraId);
						
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPrerrequisito|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_PRERREQUISITO "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999') " +
					"AND CARRERA_ID = ?");
			ps.setString(1, folio);
			ps.setString(2, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPrerrequisito|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		carreraId 		= rs.getString("CARRERA_ID");
		folio			= rs.getString("FOLIO");
		nombre			= rs.getString("NOMBRE");
		
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT CARRERA_ID, FOLIO, NOMBRE, "+
			"FROM SALOMON.ADM_PRERREQUISITO "+ 
			"WHERE FOLIO = TO_NUMBER(?,'9999999') " +
			"AND CARRERA_ID = ?");
		ps.setString(1, folio);
		ps.setString(2, carreraId);		
		
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_PRERREQUISITO "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					"AND CARRERA_ID = ? ");
			ps.setString(1,folio);
			ps.setString(2,carreraId);	
			
			
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM SALOMON.ADM_PRERREQUISITO "+ 
					"WHERE CARRERA_ID = ?");			
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