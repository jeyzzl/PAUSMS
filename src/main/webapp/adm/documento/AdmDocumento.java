// Bean del Catalogo de Documentos de Admision
package  adm.documento;

import java.sql.*;

public class AdmDocumento{
	private String documentoId;
	private String documentoNombre;
	private String tipo;
	private String comentario;
	private String original;
	private String orden;
	private String formatoId;
	
	
	public AdmDocumento(){
		documentoId 		= "";
		documentoNombre		= "";
		tipo 				= "";
		comentario			= "";
		original			= "";
		orden				= "";
		formatoId				= "";
	}
	
	
	public String getFormatoId() {
		return formatoId;
	}


	public void setFormatoId(String formatoId) {
		this.formatoId = formatoId;
	}


	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
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
	 * @return the documentoNombre
	 */
	public String getDocumentoNombre() {
		return documentoNombre;
	}



	/**
	 * @param documentoNombre the documentoNombre to set
	 */
	public void setDocumentoNombre(String documentoNombre) {
		this.documentoNombre = documentoNombre;
	}

	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * @return the original
	 */
	public String getOriginal() {
		return original;
	}


	/**
	 * @param original the original to set
	 */
	public void setOriginal(String original) {
		this.original = original;
	}


	/**
	 * @param conn the conection to set
	 * @return a value boolean (true,false)
	 */
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"SALOMON.ADM_DOCUMENTO(DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID) "+
				"VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?, '99'))");
			ps.setString(1, documentoId);
			ps.setString(2, documentoNombre);	
			ps.setString(3, tipo);
			ps.setString(4, comentario);
			ps.setString(5, original);
			ps.setString(6, orden);
			ps.setString(7, formatoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AdmDocumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	/**
	 * @param conn the conection to set
	 * @return a value boolean (true,false)
	 */
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_DOCUMENTO"+
				" SET DOCUMENTO_NOMBRE = ?," +
				" TIPO = ?," +
				" COMENTARIO = ?," +
				" ORIGINAL = ?, " +
				" ORDEN = TO_NUMBER(?,'99')," +
				" FORMATO_ID = TO_NUMBER(?, '99')" +
				" WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, documentoNombre);
			ps.setString(2, tipo);
			ps.setString(3, comentario);
			ps.setString(4, original);
			ps.setString(5, orden);
			ps.setString(6, formatoId);
			ps.setString(7, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	/**
	 * @param conn the conection to set
	 * @return a value boolean (true,false)
	 */
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_DOCUMENTO "+
				"WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		documentoId 		= rs.getString("DOCUMENTO_ID");
		documentoNombre 	= rs.getString("DOCUMENTO_NOMBRE");		
		tipo 				= rs.getString("TIPO");
		comentario 			= rs.getString("COMENTARIO");
		original 			= rs.getString("ORIGINAL");
		orden 				= rs.getString("ORDEN");
		formatoId 			= rs.getString("FORMATO_ID");
	}
	
	public void mapeaRegId( Connection conn, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID "+
				"FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1,documentoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT DOCUMENTO_ID FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99') ");
			ps.setString(1,documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(DOCUMENTO_ID)+1 MAXIMO FROM SALOMON.ADM_DOCUMENTO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreDocumento(Connection conn, String documentoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT DOCUMENTO_NOMBRE FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = ?");
			ps.setString(1, documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("DOCUMENTO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|getNombreDocumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getTipo(Connection conn, String documentoId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String tipo				= "9";
		
		try{
			ps = conn.prepareStatement("SELECT TIPO FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = ?");
			ps.setString(1, documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("TIPO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|getTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
}