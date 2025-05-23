package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdmAsesorUtil {
	

	public void mapeaRegId( Connection conn, String asesorId ) throws SQLException{
		AdmAsesor admAsesor = new AdmAsesor();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT ASESOR_ID, CORREO,CHAT, TELEFONO" +
					" FROM SALOMON.ADM_ASESOR "+ 
					" WHERE ASESOR_ID = ?");
			
			ps.setString(1, asesorId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				admAsesor.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AdmAsesorUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn, String asesorId ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ASESOR " + " WHERE ASESOR_ID = ?");
			ps.setString(1, asesorId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AdmAsesorUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}