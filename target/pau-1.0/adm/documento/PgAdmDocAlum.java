/**
 * 
 */
package adm.documento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ifo
 *
 */
public class PgAdmDocAlum {
	private String folio;
	private String documentoId;
	private String hoja;
	private int imagen;
	
	public PgAdmDocAlum(){
		folio		= "";
		documentoId	= "";
		hoja		= "";
		imagen		= 0;
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
	 * @return the documentoId
	 */
	public String getDocumentoId() {
		return documentoId;
	}

	/**
	 * @param documentoId the documentoId to set
	 */
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}

	/**
	 * @return the imagen
	 */
	public int getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(int imagen) {
		this.imagen = imagen;
	}
	
	
	/**
	 * @return the hoja
	 */
	public String getHoja() {
		return hoja;
	}

	/**
	 * @param hoja the hoja to set
	 */
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}
	

	public boolean insertReg(Connection Conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO ADM_DOCALUM"+ 
				"(FOLIO, DOCUMENTO_ID, HOJA, IMAGEN)"+
				" VALUES(TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?)");
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
			ps.setString(3, hoja);
			ps.setInt(4, imagen);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.PgAdmDocAlum|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection Conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("UPDATE ADM_DOCALUM"+ 
				" SET IMAGEN = ?"+
				" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" +
				" AND HOJA = TO_NUMBER(?,'99')");

			ps.setInt(1, imagen);
			ps.setString(2, folio);
			ps.setString(3, documentoId);
			ps.setString(4, hoja);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.PgAdmDocAlum|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
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
			System.out.println("Error - adm.documento.PgAdmDocAlum|unlink - deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		ps = null;
		if(ok){
			ok = false;
			try{
				ps = conn.prepareStatement("DELETE FROM ADM_DOCALUM"+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
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
				System.out.println("Error - adm.documento.PgAdmDocAlum|borrar - deleteReg|:"+ex);
				ok = false;
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se pudo desligar la imagen... - adm.documento.PgAdmDocAlum|deleteReg");
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");
		hoja	 		= rs.getString("HOJA");
		imagen 			= rs.getInt("IMAGEN");
	}
	
	public void mapeaRegId(Connection conn, String folio, String documentoId, String hoja) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, HOJA, IMAGEN"+
			" FROM ADM_DOCALUM" + 
			" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
			" AND DOCUMENTO_ID = TO_NUMBER(?, '99')" +
			" AND HOJA = TO_NUMBER(?, '99')");
		
		ps.setString(1,folio);
		ps.setString(2,documentoId);
		ps.setString(3, hoja);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
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
			System.out.println("Error - adm.documento.PgAdmDocAlum|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeDocumentos(Connection conn) throws SQLException{
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
			System.out.println("Error - adm.documento.PgAdmDocAlum|existeDocumentos|:"+ex);
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
			System.out.println("Error - adm.documento.PgAdmDocAlum|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
}