package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmArchivoUtil {
	
	
	public boolean insertReg(Connection conn, AdmArchivo admArchivo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ADM_ARCHIVO"+
				"(FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE, FECHA)"+
				" VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI'))");
			
			ps.setString(1, admArchivo.getFolio());
			ps.setString(2, admArchivo.getDocumentoId());
			ps.setLong(3, admArchivo.getArchivo());
			ps.setString(4, admArchivo.getNombre());
			ps.setString(5, admArchivo.getFecha());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmArchivoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AdmArchivo admArchivo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ADM_ARCHIVO"+
				" SET ARCHIVO = ?,"+
				" NOMBRE = ?"+
				" FECHA = TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI')"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" );

			ps.setLong(1, admArchivo.getArchivo());
			ps.setString(2, admArchivo.getNombre());
			ps.setString(3, admArchivo.getFecha());
			ps.setString(4, admArchivo.getFolio());
			ps.setString(5, admArchivo.getDocumentoId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmArchivoUtil|updateReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT LO_UNLINK(ARCHIVO) AS RESULTADO FROM ADM_ARCHIVO"+
			" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
			" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" );
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
		
			
			rs = ps.executeQuery();
			if(rs.next()){
			    ok=rs.getInt("RESULTADO")==1?true:false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmArchivoUtil|unlink - deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
				ps = conn.prepareStatement("DELETE FROM ADM_ARCHIVO"+
					" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
				
				ps.setString(1, folio);
				ps.setString(2, documentoId);
	
				
				if (ps.executeUpdate()== 1)
					ok = true;
				else
					ok = false;
			}catch(Exception ex){
				System.out.println("Error - aca.admision.AdmArchivoUtil|borrar - deleteReg|:"+ex);
				ok = false;
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se pudo desligar la imagen... - adm.documento.AdmArchivo|deleteReg");
		}
		
		return ok;
	}
	
	public void mapeaRegId(Connection conn, String folio, String documentoId ) throws SQLException{
		AdmArchivo admArchivo = new AdmArchivo();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE, FECHA"+
				" FROM ADM_ARCHIVO" +
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,folio);
			ps.setString(2,documentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				admArchivo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmArchivoUtil|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ADM_ARCHIVO"+
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
			System.out.println("Error - aca.admision.AdmArchivoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}