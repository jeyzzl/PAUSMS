package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PgAdmArchivosUtil {
	
	
	public boolean insertReg(Connection conn, PgAdmArchivos admArchivos) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ADM_ARCHIVOS"+
				"(FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE)"+
				" VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?)");
			
			ps.setString(1, admArchivos.getFolio());
			ps.setString(2, admArchivos.getDocumentoId());
			ps.setLong(3, admArchivos.getArchivo());
			ps.setString(4, admArchivos.getNombre());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmArchivosUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, PgAdmArchivos admArchivos) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ADM_ARCHIVOS"+
				" SET ARCHIVO = ?,"+
				" NOMBRE = ?"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" );

			ps.setLong(1, admArchivos.getArchivo());
			ps.setString(2, admArchivos.getNombre());
			ps.setString(3, admArchivos.getFolio());
			ps.setString(4, admArchivos.getDocumentoId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmArchivosUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio, String documentoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ResultSet rs = null;
			ps = conn.prepareStatement("SELECT LO_UNLINK(ARCHIVO) AS RESULTADO FROM ADM_ARCHIVOS"+
			" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
			" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" );
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
		
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=rs.getInt("RESULTADO")==1?true:false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmArchivosUtil|unlink - deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
				ps = conn.prepareStatement("DELETE FROM ADM_ARCHIVOS"+
					" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
				
				ps.setString(1, folio);
				ps.setString(2, documentoId);
	
				
				if (ps.executeUpdate()== 1)
					ok = true;
				else
					ok = false;
			}catch(Exception ex){
				System.out.println("Error - aca.admision.PgAdmArchivosUtil|borrar - deleteReg|:"+ex);
				ok = false;
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se pudo desligar la imagen... - adm.documento.PgAdmArchivos|deleteReg");
		}
		
		return ok;
	}
	
	public void mapeaRegId(Connection conn, String folio, String documentoId ) throws SQLException{
		PgAdmArchivos admArchivos = new PgAdmArchivos();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE"+
				" FROM ADM_ARCHIVOS" +
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,folio);
			ps.setString(2,documentoId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				admArchivos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.PgAdmArchivosUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn, String folio, String documentoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ADM_ARCHIVOS"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999') " +
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
			System.out.println("Error - aca.admision.PgAdmArchivosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}