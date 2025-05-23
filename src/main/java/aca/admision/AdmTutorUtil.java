package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmTutorUtil {
	
	
	public boolean insertReg(Connection conn, AdmTutor admTutor) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (admTutor.getEstado().equals("")||admTutor.getEstado()==null) admTutor.setEstado(" ");
			if (admTutor.getCiudad().equals("")||admTutor.getCiudad()==null) admTutor.setCiudad(" ");
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_TUTOR"+ 
				"(FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD) "+
				" VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9'), ?, ?, ?, ?, ?, " +
				" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?,?, ? )");
			ps.setString(1, admTutor.getFolio());
			ps.setString(2, admTutor.getTutor());
			ps.setString(3, admTutor.getNombre());
			ps.setString(4, admTutor.getCalle());
			ps.setString(5, admTutor.getNumero());
			ps.setString(6, admTutor.getColonia());
			ps.setString(7, admTutor.getCodigoPostal());
			ps.setString(8, admTutor.getPaisId());
			ps.setString(9, admTutor.getEstadoId());
			ps.setString(10, admTutor.getCiudadId());
			ps.setString(11, admTutor.getTelefono());
			ps.setString(12, admTutor.getEstado());
			ps.setString(13, admTutor.getCiudad());

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutorUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AdmTutor admTutor ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_TUTOR SET " +
					" TUTOR = TO_NUMBER(?,'9')," +		
					" NOMBRE = ?, " +
					" CALLE = ?, " +
					" NUMERO = ?, " +		
					" COLONIA = ?, " +		
					" CODIGOPOSTAL = ?, " +		
					" PAIS_ID = TO_NUMBER(?,'999'), " +		
					" ESTADO_ID = TO_NUMBER(?,'999'), " +	
					" CIUDAD_ID = TO_NUMBER(?,'999'), " +
					" TELEFONO = ?, " +
					" ESTADO = ?, " +
					" CIUDAD = ? " +			
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, admTutor.getTutor());
			ps.setString(2, admTutor.getNombre());
			ps.setString(3, admTutor.getCalle());
			ps.setString(4, admTutor.getNumero());
			ps.setString(5, admTutor.getColonia());
			ps.setString(6, admTutor.getCodigoPostal());
			ps.setString(7, admTutor.getPaisId());
			ps.setString(8, admTutor.getEstadoId());
			ps.setString(9, admTutor.getCiudadId());
			ps.setString(10, admTutor.getTelefono());
			ps.setString(11, admTutor.getEstado());
			ps.setString(12, admTutor.getCiudad());
			ps.setString(13, admTutor.getFolio());

			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutorUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_TUTOR "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutorUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public AdmTutor mapeaRegId( Connection conn, String folio ) throws SQLException{
		AdmTutor admTutor = new AdmTutor();
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, " +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD " +
				" FROM SALOMON.ADM_TUTOR "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			admTutor.mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return admTutor;
	}
	
	public boolean existeReg(Connection conn, String folio ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_TUTOR "+ 
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