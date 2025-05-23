//Bean del Catálogo de Documentos

package aca.portafolio;

import java.sql.*;

public class PorDocumento {

	private String documentoId;
	private String documentoNombre;
	private String usuario;
	private String archivo;
	
	public PorDocumento(){
		documentoId 	= "";
		documentoNombre		= "";
		usuario	= "";		
		archivo		= "";
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
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_DOCUMENTO( DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO,  ARCHIVO) "+
				"VALUES( ?, ?, ?, ?)");
			ps.setString(1, documentoId);
			ps.setString(2, documentoNombre);
			ps.setString(3, usuario);			
			ps.setString(4, archivo);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_DOCUMENTO "+ 
				"SET DOCUMENTO_NOMBRE = ?, USUARIO = ?, ARCHIVO = ? WHERE DOCUMENTO_ID = ? ");
			ps.setString(1, documentoNombre);
			ps.setString(2, usuario);			
			ps.setString(3, archivo);
			ps.setString(4, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM DANIEL.POR_DOCUMENTO "+ 
				"WHERE DOCUMENTO_ID = ?");
			ps.setString(1, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		documentoId 		= rs.getString("DOCUMENTO_ID");
		documentoNombre 	= rs.getString("DOCUMENTO_NOMBRE");
		usuario 			= rs.getString("USUARIO");
		archivo 			= rs.getString("ARCHIVO");
	}
	
	public void mapeaRegId( Connection conn, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO" +
					" FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ?"); 
			ps.setString(1, documentoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = ?"); 
			ps.setString(1,documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getNombre(Connection conn, String documentoId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT DOCUMENTO_NOMBRE FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = '"+documentoId+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("DOCUMENTO_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getArchivo(Connection conn, String documentoId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT ARCHIVO FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID = '"+documentoId+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("ARCHIVO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumento|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

	
}