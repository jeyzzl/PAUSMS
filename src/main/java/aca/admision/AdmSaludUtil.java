package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmSaludUtil {
	

	public boolean insertReg(Connection conn, AdmSalud admSalud) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			if (admSalud.getEnfermedad().equals("N")) admSalud.setEnfermedadDato("-");
			if (admSalud.getImpedimento().equals("N")) admSalud.setImpedimentoDato("-");
			
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_SALUD"+ 
				"(FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO) "+
				"VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?)");
			ps.setString(1, admSalud.getFolio());
			ps.setString(2, admSalud.getEnfermedad());
			ps.setString(3, admSalud.getEnfermedadDato());
			ps.setString(4, admSalud.getImpedimento());
			ps.setString(5, admSalud.getImpedimentoDato());

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSaludUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AdmSalud admSalud ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (admSalud.getEnfermedad().equals("N")) admSalud.setEnfermedadDato("-");
			if (admSalud.getImpedimento().equals("N")) admSalud.setImpedimentoDato("-");
			
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_SALUD " + 
					" SET ENFERMEDAD = ?, " +
					" ENFERMEDAD_DATO = ?, " +
					" IMPEDIMENTO = ?, " +
					" IMPEDIMENTO_DATO = ? " +		
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, admSalud.getEnfermedad());
			ps.setString(2, admSalud.getEnfermedadDato());
			ps.setString(3, admSalud.getImpedimento());
			ps.setString(4, admSalud.getImpedimentoDato());
			ps.setString(5, admSalud.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSaludUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_SALUD "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSaludUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public AdmSalud mapeaRegId( Connection conn, String folio ) throws SQLException{
		AdmSalud admSalud = new AdmSalud();
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO" +
				" FROM SALOMON.ADM_SALUD "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			admSalud.mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }	
		
		return admSalud;
	}
	
	public boolean existeReg(Connection conn, String folio ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_SALUD "+ 
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