package  aca.admision;

import java.sql.*;

public class AdmModCarrera{
	private String modalidadId;
	private String carreraId;
		
	
	public AdmModCarrera(){
		modalidadId = "";
		carreraId 	= "";
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public boolean insertReg(Connection Conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO SALOMON.ADM_MODCARRERA "+
				" (MODALIDAD_ID, CARRERA_ID)"+
				" VALUES(TO_NUMBER(?,'99'), ?)");
			
			ps.setString(1, modalidadId);
			ps.setString(2, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmModCarrera|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID=?");
			
			ps.setString(1, modalidadId);
			ps.setString(2, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmModCarrera|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		modalidadId = rs.getString("MODALIDAD_ID");
		carreraId 	= rs.getString("CARRERA_ID");
	}
	
	public void mapeaRegId( Connection conn, String formatoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM SALOMON.ADM_MODCARRERA" +
				" WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID = ?");
			
			ps.setString(1, modalidadId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmModCarrera|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM SALOMON.ADM_MODCARRERA "+
							" WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID = ?");
			
			ps.setString(1, modalidadId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmModCarrera|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
}