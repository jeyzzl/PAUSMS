package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PgAdmDocAlumUtil {
	
	
	public boolean insertReg(Connection conn, PgAdmDocAlum admDocAlum) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_DOCALUM"+ 
					"(FOLIO, DOCUMENTO_ID, HOJA, IMAGEN)"+
					" VALUES(TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?)");
			
			ps.setString(1, admDocAlum.getFolio());
			ps.setString(2, admDocAlum.getDocumentoId());
			ps.setString(3, admDocAlum.getHoja());
			ps.setLong(4, admDocAlum.getImagen());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, PgAdmDocAlum admDocAlum) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ADM_DOCALUM"+ 
					" SET IMAGEN = ?"+
					" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" +
					" AND HOJA = TO_NUMBER(?,'99')");

			ps.setLong(1, admDocAlum.getImagen());
			ps.setString(2, admDocAlum.getFolio());
			ps.setString(3, admDocAlum.getDocumentoId());
			ps.setString(4, admDocAlum.getHoja());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio, String documentoId, String hoja ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
			ps = conn.prepareStatement("SELECT LO_UNLINK(IMAGEN) AS RESULTADO FROM ADM_DOCALUM"+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" +
					" AND HOJA = TO_NUMBER(?,'99')");
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=rs.getInt("RESULTADO")==1?true:false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|unlink - deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
				ps = conn.prepareStatement("DELETE FROM ADM_DOCALUM"+ 
						" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
						" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" +
						" AND HOJA = TO_NUMBER(?,'99')");
				
				ps.setString(1, folio);
				ps.setString(2, documentoId);
				ps.setString(3, hoja);
				
				if (ps.executeUpdate()== 1)
					ok = true;
				else
					ok = false;
			}catch(Exception ex){
				System.out.println("Error - aca.admision.PgAdmDocAlumUtil|borrar - deleteReg|:"+ex);
				ok = false;
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se pudo desligar la imagen... - aca.admision.PgAdmDocAlum|deleteReg");
		}
		
		return ok;
	}
	
	public PgAdmDocAlum mapeaRegId(Connection conn, String folio, String documentoId, String hoja) throws SQLException{
		PgAdmDocAlum admDocAlum = new PgAdmDocAlum();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, HOJA, IMAGEN"+
					" FROM ADM_DOCALUM" + 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?, '99')" +
					" AND HOJA = TO_NUMBER(?, '99')");
			
			ps.setString(1,folio);
			ps.setString(2,documentoId);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			if (rs.next()){
				admDocAlum.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return admDocAlum;
	}
	
	public boolean existeReg(Connection conn, String folio, String documentoId, String hoja) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ADM_DOCALUM"+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
					" AND DOCUMENTO_ID = TO_NUMBER(?, '99')" +
					" AND HOJA = TO_NUMBER(?, '99')");
			
			ps.setString(1,folio);
			ps.setString(2,documentoId);
			ps.setString(3, hoja);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeDocumentos(Connection conn, String folio, String documentoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ADM_DOCALUM"+ 
				" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,folio);
			ps.setString(2,documentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|existeDocumentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String folio, String documentoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ADM_DOCALUM"+ 
				" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmDocAlumUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
}