// Bean de documentos de admision del alumno
package  adm.documento;

import java.sql.*;

public class AdmDocAlum{
	private String folio;
	private String documentoId;	
	private String estado;
	private String usuario;
	private String comentario;
	private String carta;
	
	
	public AdmDocAlum(){
		folio 			= "";
		documentoId 	= "";
		estado			= "-";
		usuario			= "-";
		comentario		= "-";
		carta			= "S";
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
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;		
	}	

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	

	/**
	 * @return the carta
	 */
	public String getCarta() {
		return carta;
	}
	
	/**
	 * @param carta the carta to set
	 */
	public void setCarta(String carta) {
		this.carta = carta;
	}


	/**
	 * @return true si inserta el renglón 
	 */
	public boolean insertReg(Connection Conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO SALOMON.ADM_DOCALUM"+ 
				"(FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?, ?, ?, 'S')");
			ps.setString(1, folio);
			ps.setString(2, documentoId);			
			ps.setString(3, estado);
			ps.setString(4, usuario);
			ps.setString(5, comentario);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmDocAlum|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
			
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_DOCALUM"+ 
				" SET ESTADO = ?, USUARIO = ?, COMENTARIO = ?, CARTA = ?"+
				" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, estado);
			ps.setString(2, usuario);
			ps.setString(3, comentario);
			ps.setString(4, carta);
			ps.setString(5, folio);
			ps.setString(6, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmDocAlum|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_DOCALUM"+ 
				" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, folio);
			ps.setString(2, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmDocAlum|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");		
		estado 			= rs.getString("ESTADO");
		usuario			= rs.getString("USUARIO");
		comentario		= rs.getString("COMENTARIO");
		carta			= rs.getString("CARTA");
	}
	
	public void mapeaRegId( Connection conn, String folio, String documentoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA"+
			" FROM SALOMON.ADM_DOCALUM " + 
			" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
			" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
		ps.setString(1, folio);
		ps.setString(2, documentoId);
		
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_DOCALUM "+ 
				"WHERE FOLIO = TO_NUMBER(?,'99999999') " +
				"AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1,folio);
			ps.setString(2,documentoId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmDocAlum|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}