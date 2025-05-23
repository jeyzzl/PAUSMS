/**
 * 
 */
package adm.documento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author etorres
 *
 */
public class PgAdmArchivos {
	private String folio;
	private String documentoId;	
	private int archivo;
	private String nombre;
	
	public PgAdmArchivos(){
		folio		= "";
		documentoId	= "";		
		archivo		= 0;
		nombre 		= "";
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
	 * @return the archivo
	 */
	public int getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(int archivo) {
		this.archivo = archivo;
	}
	
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public boolean insertReg(Connection Conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO ADM_ARCHIVOS"+
				"(FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE)"+
				" VALUES(TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?)");
			
			ps.setString(1, folio);
			ps.setString(2, documentoId);
			ps.setInt(3, archivo );
			ps.setString(4, nombre);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.PgAdmArchivos|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection Conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("UPDATE ADM_ARCHIVOS"+
				" SET ARCHIVO = ?,"+
				" NOMBRE = ?"+
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')" );

			ps.setInt(1,archivo );
			ps.setString(2, nombre);
			ps.setString(3, folio);
			ps.setString(4, documentoId);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.PgAdmArchivos|updateReg|:"+ex);
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
			System.out.println("Error - adm.documento.PgAdmArchivos|unlink - deleteReg|:"+ex);
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
				System.out.println("Error - adm.documento.PgAdmArchivos|borrar - deleteReg|:"+ex);
				ok = false;
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}else{
			System.out.println("No se pudo desligar la imagen... - aca.admision..PgAdmArchivos|deleteReg");
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		documentoId	 	= rs.getString("DOCUMENTO_ID");		
		archivo			= rs.getInt("ARCHIVO");
		nombre		 	= rs.getString("NOMBRE");
	}
	
	public void mapeaRegId(Connection conn, String folio, String documentoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, DOCUMENTO_ID, ARCHIVO, NOMBRE"+
			" FROM ADM_ARCHIVOS" +
			" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
			" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
		
		ps.setString(1,folio);
		ps.setString(2,documentoId);		
		
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
			System.out.println("Error - adm.documento.PgAdmArchivos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}