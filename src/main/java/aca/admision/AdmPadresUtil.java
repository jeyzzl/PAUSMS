package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmPadresUtil {
	
	
	public boolean insertReg(Connection conn, AdmPadres admPadres) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_PADRES"+ 
				"(FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD, PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, " +
				" MADRE_NACIONALIDAD, MADRE_OCUPACION ) "+
				"VALUES( TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ? )");
			ps.setString(1, admPadres.getFolio());
			ps.setString(2, admPadres.getPadreNombre());
			ps.setString(3, admPadres.getPadreApellido());
			ps.setString(4, admPadres.getPadreReligion());
			ps.setString(5, admPadres.getPadreNacionalidad());
			ps.setString(6, admPadres.getPadreOcupacion());
			ps.setString(7, admPadres.getMadreNombre());
			ps.setString(8, admPadres.getMadreApellido());
			ps.setString(9, admPadres.getMadreReligion());
			ps.setString(10, admPadres.getMadreNacionalidad());
			ps.setString(11, admPadres.getMadreOcupacion());

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadresUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AdmPadres admPadres ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_PADRES " + 
					" SET PADRE_NOMBRE = ?, " +
					" PADRE_APELLIDO = ?, " +
					" PADRE_RELIGION = TO_NUMBER(?,'99'), " +
					" PADRE_NACIONALIDAD = TO_NUMBER(?,'999'), " +	
					" PADRE_OCUPACION = ?, " +	
					" MADRE_NOMBRE = ?, " +
					" MADRE_APELLIDO = ?, " +
					" MADRE_RELIGION = TO_NUMBER(?,'99'), " +
					" MADRE_NACIONALIDAD = TO_NUMBER(?,'999'), " +	
					" MADRE_OCUPACION = ? " +			
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, admPadres.getPadreNombre());
			ps.setString(2, admPadres.getPadreApellido());
			ps.setString(3, admPadres.getPadreReligion());
			ps.setString(4, admPadres.getPadreNacionalidad());
			ps.setString(5, admPadres.getPadreOcupacion());
			ps.setString(6, admPadres.getMadreNombre());
			ps.setString(7, admPadres.getMadreApellido());
			ps.setString(8, admPadres.getMadreReligion());
			ps.setString(9, admPadres.getMadreNacionalidad());
			ps.setString(10, admPadres.getMadreOcupacion());
			ps.setString(11, admPadres.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadresUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_PADRES "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadresUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public AdmPadres mapeaRegId( Connection conn, String folio ) throws SQLException{
		AdmPadres admPadres = new AdmPadres();
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD," +
				" PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, MADRE_NACIONALIDAD, MADRE_OCUPACION " +
				" FROM SALOMON.ADM_PADRES "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			admPadres.mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return admPadres;
	}
	
	public boolean existeReg(Connection conn, String folio ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_PADRES "+ 
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